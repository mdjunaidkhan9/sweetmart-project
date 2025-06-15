package com.project.customer.service;

import com.project.customer.model.Customer;
import com.project.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository repo;

    @InjectMocks
    private CustomerService service;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L, "user1", "Alice", "alice@email.com", "123", "addr1");
    }

    // --- saveCustomer ---
    @Test
    void saveCustomer_NullUsername() {
        ResponseEntity<Customer> resp = service.saveCustomer(null, customer);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void saveCustomer_EmptyUsername() {
        ResponseEntity<Customer> resp = service.saveCustomer("   ", customer);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void saveCustomer_NullCustomer() {
        ResponseEntity<Customer> resp = service.saveCustomer("user1", null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void saveCustomer_Valid() {
        when(repo.save(any(Customer.class))).thenReturn(customer);
        ResponseEntity<Customer> resp = service.saveCustomer("user1", customer);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(customer, resp.getBody());
        assertEquals("user1", customer.getUsername());
    }

    // --- getCustomer ---
    @Test
    void getCustomer_NullUsername() {
        ResponseEntity<Customer> resp = service.getCustomer(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getCustomer_EmptyUsername() {
        ResponseEntity<Customer> resp = service.getCustomer("   ");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getCustomer_NotFound() {
        when(repo.findByUsername("user1")).thenReturn(Optional.empty());
        ResponseEntity<Customer> resp = service.getCustomer("user1");
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    void getCustomer_Found() {
        when(repo.findByUsername("user1")).thenReturn(Optional.of(customer));
        ResponseEntity<Customer> resp = service.getCustomer("user1");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(customer, resp.getBody());
    }

    // --- getAllCustomers ---
    @Test
    void getAllCustomers_Empty() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Customer>> resp = service.getAllCustomers();
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getAllCustomers_NotEmpty() {
        when(repo.findAll()).thenReturn(List.of(customer));
        ResponseEntity<List<Customer>> resp = service.getAllCustomers();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
        assertEquals(customer, resp.getBody().get(0));
    }

    // --- getAllCustomersSortedByName ---
    @Test
    void getAllCustomersSortedByName_Empty() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Customer>> resp = service.getAllCustomersSortedByName();
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getAllCustomersSortedByName_SortedAndNullSafe() {
        Customer c1 = new Customer(1L, "user1", "Bob", "bob@email.com", "123", "addr1");
        Customer c2 = new Customer(2L, "user2", null, "alice@email.com", "456", "addr2");
        Customer c3 = new Customer(3L, "user3", "Alice", "charlie@email.com", "789", "addr3");
        when(repo.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
        ResponseEntity<List<Customer>> resp = service.getAllCustomersSortedByName();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        List<Customer> sorted = resp.getBody();
        assertEquals("Alice", sorted.get(0).getName());
        assertEquals("Bob", sorted.get(1).getName());
        assertNull(sorted.get(2).getName());
    }

    // --- getAllCustomersSortedByEmail ---
    @Test
    void getAllCustomersSortedByEmail_Empty() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Customer>> resp = service.getAllCustomersSortedByEmail();
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        assertTrue(resp.getBody().isEmpty());
    }

    @Test
    void getAllCustomersSortedByEmail_SortedAndNullSafe() {
        Customer c1 = new Customer(1L, "user1", "Alice", "bob@email.com", "123", "addr1");
        Customer c2 = new Customer(2L, "user2", "Bob", null, "456", "addr2");
        Customer c3 = new Customer(3L, "user3", "Charlie", "alice@email.com", "789", "addr3");
        when(repo.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
        ResponseEntity<List<Customer>> resp = service.getAllCustomersSortedByEmail();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        List<Customer> sorted = resp.getBody();
        assertEquals("alice@email.com", sorted.get(0).getEmail());
        assertEquals("bob@email.com", sorted.get(1).getEmail());
        assertNull(sorted.get(2).getEmail());
    }

    // --- deleteCustomer ---
    @Test
    void deleteCustomer_NullId() {
        ResponseEntity<String> resp = service.deleteCustomer(null);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("ID must not be null.", resp.getBody());
    }

    @Test
    void deleteCustomer_NotFound() {
        when(repo.existsById(99L)).thenReturn(false);
        ResponseEntity<String> resp = service.deleteCustomer(99L);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals("Customer not found with ID: 99", resp.getBody());
    }

    @Test
    void deleteCustomer_Success() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);
        ResponseEntity<String> resp = service.deleteCustomer(1L);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Customer deleted with ID: 1", resp.getBody());
    }
}
