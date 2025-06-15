package com.project.sweetorder.controller;

import com.project.sweetorder.model.SweetOrder;
import com.project.sweetorder.service.SweetOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweetOrderControllerTest {

    @InjectMocks
    private SweetOrderController controller;

    @Mock
    private SweetOrderService orderService;

    private SweetOrder order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new SweetOrder();
        order.setId(1L);
        order.setUsername("junaid");
        order.setTotalAmount(500.0);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(List.of());
    }

    @Test
    void placeOrder_valid() {
        ResponseEntity<SweetOrder> expected = ResponseEntity.ok(order);
        when(orderService.placeOrder("junaid")).thenReturn(expected);
        ResponseEntity<SweetOrder> resp = controller.placeOrder("junaid");
        assertEquals(expected, resp);
        verify(orderService, times(1)).placeOrder("junaid");
    }

    @Test
    void getOrders_valid() {
        ResponseEntity<List<SweetOrder>> expected = ResponseEntity.ok(List.of(order));
        when(orderService.getOrders("junaid")).thenReturn(expected);
        ResponseEntity<List<SweetOrder>> resp = controller.getOrders("junaid");
        assertEquals(expected, resp);
        verify(orderService, times(1)).getOrders("junaid");
    }

    @Test
    void updateOrders_valid() {
        ResponseEntity<List<SweetOrder>> expected = ResponseEntity.ok(List.of(order));
        when(orderService.updateOrders("junaid", order)).thenReturn(expected);
        ResponseEntity<List<SweetOrder>> resp = controller.updateOrders("junaid", order);
        assertEquals(expected, resp);
        verify(orderService, times(1)).updateOrders("junaid", order);
    }

    @Test
    void deleteOrdersByUsername_valid() {
        ResponseEntity<String> expected = ResponseEntity.ok("Orders deleted for user: junaid");
        when(orderService.deleteOrdersByUsername("junaid")).thenReturn(expected);
        ResponseEntity<String> resp = controller.deleteOrdersByUsername("junaid");
        assertEquals(expected, resp);
        verify(orderService, times(1)).deleteOrdersByUsername("junaid");
    }

    @Test
    void getAllOrders_valid() {
        ResponseEntity<List<SweetOrder>> expected = ResponseEntity.ok(List.of(order));
        when(orderService.getAllOrders()).thenReturn(expected);
        ResponseEntity<List<SweetOrder>> resp = controller.getAllOrders();
        assertEquals(expected, resp);
        verify(orderService, times(1)).getAllOrders();
    }
}
