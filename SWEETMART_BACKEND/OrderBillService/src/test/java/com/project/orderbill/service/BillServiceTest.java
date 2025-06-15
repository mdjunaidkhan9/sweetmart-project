package com.project.orderbill.service;

import com.project.orderbill.dto.CustomerDTO;
import com.project.orderbill.dto.SweetOrderDTO;
import com.project.orderbill.entity.Bill;
import com.project.orderbill.feign.CustomerClient;
import com.project.orderbill.feign.SweetOrderClient;
import com.project.orderbill.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillServiceTest {

    @Mock
    private SweetOrderClient sweetOrderClient;
    @Mock
    private CustomerClient customerClient;
    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillService billService;

    private Bill bill;
    private SweetOrderDTO orderDTO;
    private CustomerDTO customerDTO;

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

        orderDTO = new SweetOrderDTO();
        orderDTO.setId(101L);
        orderDTO.setTotalAmount(500.0);

        customerDTO = new CustomerDTO();
        customerDTO.setName("Junaid Khan");
        customerDTO.setShippingAddress("123 Street, NY");
    }

    @Test
    void createBill_NullUsername() {
        ResponseEntity<Bill> resp = billService.createBill(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void createBill_EmptyUsername() {
        ResponseEntity<Bill> resp = billService.createBill("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void createBill_NoOrders() {
        when(sweetOrderClient.getOrders("junaid")).thenReturn(Collections.emptyList());
        ResponseEntity<Bill> resp = billService.createBill("junaid");
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void createBill_Success() {
        when(sweetOrderClient.getOrders("junaid")).thenReturn(List.of(orderDTO));
        when(customerClient.getCustomer("junaid")).thenReturn(customerDTO);
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        ResponseEntity<Bill> resp = billService.createBill("junaid");
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(bill, resp.getBody());
    }

    @Test
    void getLatestBill_NullUsername() {
        ResponseEntity<Bill> resp = billService.getLatestBill(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getLatestBill_EmptyUsername() {
        ResponseEntity<Bill> resp = billService.getLatestBill("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getLatestBill_NotFound() {
        when(billRepository.findTopByUsernameOrderByCreatedDateDesc("junaid")).thenReturn(null);
        ResponseEntity<Bill> resp = billService.getLatestBill("junaid");
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getLatestBill_Found() {
        when(billRepository.findTopByUsernameOrderByCreatedDateDesc("junaid")).thenReturn(bill);
        ResponseEntity<Bill> resp = billService.getLatestBill("junaid");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(bill, resp.getBody());
    }

    @Test
    void getAllBills_Empty() {
        when(billRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Bill>> resp = billService.getAllBills();
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getAllBills_Found() {
        when(billRepository.findAll()).thenReturn(List.of(bill));
        ResponseEntity<List<Bill>> resp = billService.getAllBills();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
        assertEquals(bill, resp.getBody().get(0));
    }
}
