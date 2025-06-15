package com.project.sweetorder.service;

import com.project.sweetorder.feign.CartFeignClient;
import com.project.sweetorder.model.CartItemDTO;
import com.project.sweetorder.model.OrderItem;
import com.project.sweetorder.model.SweetOrder;
import com.project.sweetorder.repository.SweetOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweetOrderServiceTest {

    @Mock
    private SweetOrderRepository orderRepo;

    @Mock
    private CartFeignClient cartClient;

    @InjectMocks
    private SweetOrderService service;

    private SweetOrder order;
    private CartItemDTO cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItem = new CartItemDTO();
        cartItem.setProductId(4);
        cartItem.setProductName("Rasgulla");
        cartItem.setQuantity(2);
        cartItem.setProductPrice(250.0);

        order = new SweetOrder();
        order.setId(1L);
        order.setUsername("junaid");
        order.setTotalAmount(500.0);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(new ArrayList<>());
    }

    // --- placeOrder ---
    @Test
    void placeOrder_NullUsername() {
        ResponseEntity<SweetOrder> resp = service.placeOrder(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void placeOrder_EmptyUsername() {
        ResponseEntity<SweetOrder> resp = service.placeOrder("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void placeOrder_EmptyCart() {
        when(cartClient.getCartByUsername("junaid")).thenReturn(Collections.emptyList());
        ResponseEntity<SweetOrder> resp = service.placeOrder("junaid");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void placeOrder_Success() {
        when(cartClient.getCartByUsername("junaid")).thenReturn(List.of(cartItem));
        when(orderRepo.save(any(SweetOrder.class))).thenReturn(order);
        doNothing().when(cartClient).clearCart("junaid");
        ResponseEntity<SweetOrder> resp = service.placeOrder("junaid");
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(order, resp.getBody());
    }

    // --- getOrders ---
    @Test
    void getOrders_NullUsername() {
        ResponseEntity<List<SweetOrder>> resp = service.getOrders(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getOrders_EmptyUsername() {
        ResponseEntity<List<SweetOrder>> resp = service.getOrders("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getOrders_EmptyOrders() {
        when(orderRepo.findByUsername("junaid")).thenReturn(Collections.emptyList());
        ResponseEntity<List<SweetOrder>> resp = service.getOrders("junaid");
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getOrders_Found() {
        when(orderRepo.findByUsername("junaid")).thenReturn(List.of(order));
        ResponseEntity<List<SweetOrder>> resp = service.getOrders("junaid");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
    }

    // --- updateOrders ---
    @Test
    void updateOrders_NullUsername() {
        ResponseEntity<List<SweetOrder>> resp = service.updateOrders(null, order);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateOrders_EmptyUsername() {
        ResponseEntity<List<SweetOrder>> resp = service.updateOrders("   ", order);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateOrders_NullOrderData() {
        ResponseEntity<List<SweetOrder>> resp = service.updateOrders("junaid", null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateOrders_NotFound() {
        when(orderRepo.findByUsername("junaid")).thenReturn(Collections.emptyList());
        ResponseEntity<List<SweetOrder>> resp = service.updateOrders("junaid", order);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void updateOrders_Success() {
        when(orderRepo.findByUsername("junaid")).thenReturn(List.of(order));
        when(orderRepo.saveAll(anyList())).thenReturn(List.of(order));
        ResponseEntity<List<SweetOrder>> resp = service.updateOrders("junaid", order);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
    }

    // --- deleteOrdersByUsername ---
    @Test
    void deleteOrdersByUsername_Null() {
        ResponseEntity<String> resp = service.deleteOrdersByUsername(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Username is required", resp.getBody());
    }

    @Test
    void deleteOrdersByUsername_Empty() {
        ResponseEntity<String> resp = service.deleteOrdersByUsername("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("Username is required", resp.getBody());
    }

    @Test
    void deleteOrdersByUsername_NotFound() {
        when(orderRepo.findByUsername("junaid")).thenReturn(Collections.emptyList());
        ResponseEntity<String> resp = service.deleteOrdersByUsername("junaid");
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals("No orders found for user: junaid", resp.getBody());
    }

    @Test
    void deleteOrdersByUsername_Success() {
        when(orderRepo.findByUsername("junaid")).thenReturn(List.of(order));
        doNothing().when(orderRepo).deleteAll(anyList());
        ResponseEntity<String> resp = service.deleteOrdersByUsername("junaid");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Orders deleted for user: junaid", resp.getBody());
    }

    // --- getAllOrders ---
    @Test
    void getAllOrders_Empty() {
        when(orderRepo.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<SweetOrder>> resp = service.getAllOrders();
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getAllOrders_Found() {
        when(orderRepo.findAll()).thenReturn(List.of(order));
        ResponseEntity<List<SweetOrder>> resp = service.getAllOrders();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
    }
}
