package com.project.cart.controller;

import com.project.cart.model.CartItem;
import com.project.cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @InjectMocks
    private CartController controller;

    @Mock
    private CartService cartService;

    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItem = new CartItem();
        cartItem.setUsername("junaid");
        cartItem.setProductName("Rasgulla");
        cartItem.setQuantity(2);
        cartItem.setProductId(4);
        cartItem.setProductPrice(250.0);
    }

    @Test
    void addToCart_valid() {
        ResponseEntity<CartItem> expected = ResponseEntity.ok(cartItem);
        when(cartService.addToCart("junaid", "Rasgulla", 2)).thenReturn(expected);
        ResponseEntity<CartItem> resp = controller.addToCart("junaid", "Rasgulla", 2);
        assertEquals(expected, resp);
        verify(cartService, times(1)).addToCart("junaid", "Rasgulla", 2);
    }

    @Test
    void getCart_valid() {
        ResponseEntity<List<CartItem>> expected = ResponseEntity.ok(List.of(cartItem));
        when(cartService.getCartByUsername("junaid")).thenReturn(expected);
        ResponseEntity<List<CartItem>> resp = controller.getCart("junaid");
        assertEquals(expected, resp);
        verify(cartService, times(1)).getCartByUsername("junaid");
    }

    @Test
    void updateQuantity_valid() {
        ResponseEntity<CartItem> expected = ResponseEntity.ok(cartItem);
        when(cartService.updateQuantity("junaid", "Rasgulla", 3)).thenReturn(expected);
        ResponseEntity<CartItem> resp = controller.updateQuantity("junaid", "Rasgulla", 3);
        assertEquals(expected, resp);
        verify(cartService, times(1)).updateQuantity("junaid", "Rasgulla", 3);
    }

    @Test
    void removeFromCart_valid() {
        ResponseEntity<String> expected = ResponseEntity.ok("Product removed from cart successfully.");
        when(cartService.removeFromCart("junaid", "Rasgulla")).thenReturn(expected);
        ResponseEntity<String> resp = controller.removeFromCart("junaid", "Rasgulla");
        assertEquals(expected, resp);
        verify(cartService, times(1)).removeFromCart("junaid", "Rasgulla");
    }

    @Test
    void clearCart_valid() {
        ResponseEntity<String> expected = ResponseEntity.ok("Cart cleared successfully.");
        when(cartService.clearCart("junaid")).thenReturn(expected);
        ResponseEntity<String> resp = controller.clearCart("junaid");
        assertEquals(expected, resp);
        verify(cartService, times(1)).clearCart("junaid");
    }
}
