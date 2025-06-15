package com.project.payment.controller;

import com.project.payment.entity.Payment;
import com.project.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    private Payment payment;
    private Map<String, Object> paymentMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment("pi_123", "junaid", 6L, 100.0, "succeeded");
        paymentMap = Map.of(
                "paymentId", "pi_123",
                "username", "junaid",
                "billId", 6L,
                "amount", 100.0,
                "status", "succeeded"
        );
    }

    @Test
    void pay_valid() {
        ResponseEntity<Map<String, Object>> expected = ResponseEntity.ok(paymentMap);
        when(paymentService.processPayment("junaid")).thenReturn(expected);
        ResponseEntity<Map<String, Object>> resp = paymentController.pay("junaid");
        assertEquals(expected, resp);
        verify(paymentService, times(1)).processPayment("junaid");
    }

    @Test
    void getPaymentDetails_valid() {
        ResponseEntity<Map<String, Object>> expected = ResponseEntity.ok(paymentMap);
        when(paymentService.getLatestPayment("junaid")).thenReturn(expected);
        ResponseEntity<Map<String, Object>> resp = paymentController.getPaymentDetails("junaid");
        assertEquals(expected, resp);
        verify(paymentService, times(1)).getLatestPayment("junaid");
    }

    @Test
    void updateStatus_valid() {
        ResponseEntity<Payment> expected = ResponseEntity.ok(payment);
        when(paymentService.updatePaymentStatus("pi_123")).thenReturn(expected);
        ResponseEntity<Payment> resp = paymentController.updateStatus("pi_123");
        assertEquals(expected, resp);
        verify(paymentService, times(1)).updatePaymentStatus("pi_123");
    }

    @Test
    void getAllPayments_valid() {
        ResponseEntity<List<Payment>> expected = ResponseEntity.ok(List.of(payment));
        when(paymentService.getAllPayments()).thenReturn(expected);
        ResponseEntity<List<Payment>> resp = paymentController.getAllPayments();
        assertEquals(expected, resp);
        verify(paymentService, times(1)).getAllPayments();
    }
}
