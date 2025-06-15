package com.project.cart.service;

import com.project.cart.feign.ProductFeignClient;
import com.project.cart.model.CartItem;
import com.project.cart.model.ProductEntity;
import com.project.cart.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartItemRepository cartRepository;

    @Mock
    private ProductFeignClient productClient;

    @InjectMocks
    private CartService service;

    private CartItem cartItem;
    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItem = new CartItem();
        cartItem.setId(2);
        cartItem.setUsername("junaid");
        cartItem.setProductId(4);
        cartItem.setProductName("Rasgulla");
        cartItem.setProductPrice(250.0);
        cartItem.setQuantity(2);

        product = new ProductEntity();
        product.setProductId(4);
        product.setProductName("Rasgulla");
        product.setProductPrice(250.0);
    }

    // --- addToCart ---
    @Test
    void addToCart_NullUsername() {
        ResponseEntity<CartItem> resp = service.addToCart(null, "Rasgulla", 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void addToCart_EmptyUsername() {
        ResponseEntity<CartItem> resp = service.addToCart("   ", "Rasgulla", 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void addToCart_NullProductName() {
        ResponseEntity<CartItem> resp = service.addToCart("junaid", null, 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void addToCart_EmptyProductName() {
        ResponseEntity<CartItem> resp = service.addToCart("junaid", "   ", 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void addToCart_QuantityZero() {
        ResponseEntity<CartItem> resp = service.addToCart("junaid", "Rasgulla", 0);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void addToCart_ExistingItem() {
        when(cartRepository.findByUsernameAndProductName("junaid", "Rasgulla")).thenReturn(cartItem);
        when(cartRepository.save(any(CartItem.class))).thenReturn(cartItem);
        ResponseEntity<CartItem> resp = service.addToCart("junaid", "Rasgulla", 3);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(5, resp.getBody().getQuantity());
    }

    @Test
    void addToCart_ProductNotFound() {
        when(cartRepository.findByUsernameAndProductName("junaid", "Rasgulla")).thenReturn(null);
        when(productClient.getProductByName("Rasgulla")).thenReturn(null);
        ResponseEntity<CartItem> resp = service.addToCart("junaid", "Rasgulla", 2);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void addToCart_NewItem() {
        when(cartRepository.findByUsernameAndProductName("junaid", "Rasgulla")).thenReturn(null);
        when(productClient.getProductByName("Rasgulla")).thenReturn(product);
        when(cartRepository.save(any(CartItem.class))).thenReturn(cartItem);
        ResponseEntity<CartItem> resp = service.addToCart("junaid", "Rasgulla", 2);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(cartItem, resp.getBody());
    }

    // --- getCartByUsername ---
    @Test
    void getCartByUsername_Null() {
        ResponseEntity<List<CartItem>> resp = service.getCartByUsername(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getCartByUsername_Empty() {
        ResponseEntity<List<CartItem>> resp = service.getCartByUsername("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getCartByUsername_EmptyCart() {
        when(cartRepository.findByUsername("junaid")).thenReturn(Collections.emptyList());
        ResponseEntity<List<CartItem>> resp = service.getCartByUsername("junaid");
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getCartByUsername_Valid() {
        when(cartRepository.findByUsername("junaid")).thenReturn(List.of(cartItem));
        ResponseEntity<List<CartItem>> resp = service.getCartByUsername("junaid");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
        assertEquals("Rasgulla", resp.getBody().get(0).getProductName());
    }

    // --- updateQuantity ---
    @Test
    void updateQuantity_NullUsername() {
        ResponseEntity<CartItem> resp = service.updateQuantity(null, "Rasgulla", 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateQuantity_EmptyUsername() {
        ResponseEntity<CartItem> resp = service.updateQuantity("   ", "Rasgulla", 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateQuantity_NullProductName() {
        ResponseEntity<CartItem> resp = service.updateQuantity("junaid", null, 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateQuantity_EmptyProductName() {
        ResponseEntity<CartItem> resp = service.updateQuantity("junaid", "   ", 1);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateQuantity_QuantityZero() {
        ResponseEntity<CartItem> resp = service.updateQuantity("junaid", "Rasgulla", 0);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateQuantity_ItemNotFound() {
        when(cartRepository.findByUsernameAndProductName("junaid", "Rasgulla")).thenReturn(null);
        ResponseEntity<CartItem> resp = service.updateQuantity("junaid", "Rasgulla", 2);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateQuantity_Success() {
        when(cartRepository.findByUsernameAndProductName("junaid", "Rasgulla")).thenReturn(cartItem);
        when(cartRepository.save(any(CartItem.class))).thenReturn(cartItem);
        ResponseEntity<CartItem> resp = service.updateQuantity("junaid", "Rasgulla", 5);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(5, resp.getBody().getQuantity());
    }

    // --- removeFromCart ---
    @Test
    void removeFromCart_NullUsername() {
        ResponseEntity<String> resp = service.removeFromCart(null, "Rasgulla");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Invalid input for removeFromCart", resp.getBody());
    }

    @Test
    void removeFromCart_EmptyUsername() {
        ResponseEntity<String> resp = service.removeFromCart("   ", "Rasgulla");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Invalid input for removeFromCart", resp.getBody());
    }

    @Test
    void removeFromCart_NullProductName() {
        ResponseEntity<String> resp = service.removeFromCart("junaid", null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Invalid input for removeFromCart", resp.getBody());
    }

    @Test
    void removeFromCart_EmptyProductName() {
        ResponseEntity<String> resp = service.removeFromCart("junaid", "   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Invalid input for removeFromCart", resp.getBody());
    }

    @Test
    void removeFromCart_ItemNotFound() {
        when(cartRepository.findByUsernameAndProductName("junaid", "Rasgulla")).thenReturn(null);
        ResponseEntity<String> resp = service.removeFromCart("junaid", "Rasgulla");
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals("Cart item not found for remove", resp.getBody());
    }

    @Test
    void removeFromCart_Success() {
        when(cartRepository.findByUsernameAndProductName("junaid", "Rasgulla")).thenReturn(cartItem);
        doNothing().when(cartRepository).delete(cartItem);
        ResponseEntity<String> resp = service.removeFromCart("junaid", "Rasgulla");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Product removed from cart successfully.", resp.getBody());
    }

    // --- clearCart ---
    @Test
    void clearCart_Null() {
        ResponseEntity<String> resp = service.clearCart(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Username is required for clearCart", resp.getBody());
    }

    @Test
    void clearCart_Empty() {
        ResponseEntity<String> resp = service.clearCart("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Username is required for clearCart", resp.getBody());
    }

    @Test
    void clearCart_EmptyCart() {
        when(cartRepository.findByUsername("junaid")).thenReturn(Collections.emptyList());
        ResponseEntity<String> resp = service.clearCart("junaid");
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertEquals("Cart already empty", resp.getBody());
    }

    @Test
    void clearCart_Success() {
        when(cartRepository.findByUsername("junaid")).thenReturn(List.of(cartItem));
        doNothing().when(cartRepository).deleteAll(anyList());
        ResponseEntity<String> resp = service.clearCart("junaid");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Cart cleared successfully.", resp.getBody());
    }
}
