package com.project.orderbill.controller;

import com.project.orderbill.entity.Bill;
import com.project.orderbill.service.BillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillControllerTest {

    @InjectMocks
    private BillController billController;

    @Mock
    private BillService billService;

    private Bill bill;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bill = new Bill();
        bill.setBillId(1L);
        bill.setUsername("junaid");
        bill.setName("Junaid Khan");
        bill.setShippingAddress("123 Street, NY");
        bill.setTotalAmount(500.0);
        bill.setCreatedDate(LocalDateTime.now());
    }

    @Test
    void createBill_valid() {
        ResponseEntity<Bill> expected = ResponseEntity.ok(bill);
        when(billService.createBill("junaid")).thenReturn(expected);
        ResponseEntity<Bill> resp = billController.createBill("junaid");
        assertEquals(expected, resp);
        verify(billService, times(1)).createBill("junaid");
    }

    @Test
    void getBill_valid() {
        ResponseEntity<Bill> expected = ResponseEntity.ok(bill);
        when(billService.getLatestBill("junaid")).thenReturn(expected);
        ResponseEntity<Bill> resp = billController.getBill("junaid");
        assertEquals(expected, resp);
        verify(billService, times(1)).getLatestBill("junaid");
    }

    @Test
    void getAllBills_valid() {
        ResponseEntity<List<Bill>> expected = ResponseEntity.ok(List.of(bill));
        when(billService.getAllBills()).thenReturn(expected);
        ResponseEntity<List<Bill>> resp = billController.getAllBills();
        assertEquals(expected, resp);
        verify(billService, times(1)).getAllBills();
    }
}
