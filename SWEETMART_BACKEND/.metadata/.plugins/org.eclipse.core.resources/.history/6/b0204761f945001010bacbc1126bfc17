package com.project.payment.service;

import com.project.payment.dto.BillDTO;
import com.project.payment.entity.Payment;
import com.project.payment.feign.OrderBillClient;
import com.project.payment.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private OrderBillClient orderBillClient;
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;
    private BillDTO billDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment("pi_123", "junaid", 6L, 100.0, "succeeded");
        billDTO = new BillDTO();
        billDTO.setBillId(6L);
        billDTO.setTotalAmount(100.0);
        billDTO.setSweetOrderId(5L);
    }

    @Test
    void processPayment_NullUsername() {
        ResponseEntity<Map<String, Object>> resp = paymentService.processPayment(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertTrue(resp.getBody().containsKey("error"));
    }

    @Test
    void processPayment_NoBill() {
        when(orderBillClient.getBillByUsername("junaid")).thenReturn(null);
        ResponseEntity<Map<String, Object>> resp = paymentService.processPayment("junaid");
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertTrue(resp.getBody().containsKey("error"));
    }

    // StripeException and PaymentIntent mocking would be needed for full test coverage of success

    @Test
    void getLatestPayment_NullUsername() {
        ResponseEntity<Map<String, Object>> resp = paymentService.getLatestPayment(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getLatestPayment_NotFound() {
        when(paymentRepository.findTopByUsernameOrderByPaymentIdDesc("junaid")).thenReturn(Optional.empty());
        ResponseEntity<Map<String, Object>> resp = paymentService.getLatestPayment("junaid");
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getLatestPayment_Found() {
        when(paymentRepository.findTopByUsernameOrderByPaymentIdDesc("junaid")).thenReturn(Optional.of(payment));
        ResponseEntity<Map<String, Object>> resp = paymentService.getLatestPayment("junaid");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("junaid", resp.getBody().get("username"));
    }

    @Test
    void updatePaymentStatus_NullId() {
        ResponseEntity<Payment> resp = paymentService.updatePaymentStatus(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getAllPayments_Empty() {
        when(paymentRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Payment>> resp = paymentService.getAllPayments();
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getAllPayments_Found() {
        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        ResponseEntity<List<Payment>> resp = paymentService.getAllPayments();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
        assertEquals(payment, resp.getBody().get(0));
    }
}
