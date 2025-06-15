package com.project.customer.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.project.customer.model.Customer;
import com.project.customer.service.CustomerService;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L, "junaid", "Junaid Khan", "junaid@example.com", "1234567890", "123 Street, NY");
    }

    @Test
    void testSaveCustomer() {
        when(customerService.saveCustomer("junaid", customer)).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity<Customer> response = customerController.save("junaid", customer);

        assertNotNull(response.getBody());
        assertEquals("Junaid Khan", response.getBody().getName());
        verify(customerService, times(1)).saveCustomer("junaid", customer);
    }

    @Test
    void testGetCustomer() {
        when(customerService.getCustomer("junaid")).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity<Customer> response = customerController.get("junaid");

        assertNotNull(response.getBody());
        assertEquals("Junaid Khan", response.getBody().getName());
        verify(customerService, times(1)).getCustomer("junaid");
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(ResponseEntity.ok(List.of(customer)));

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        assertFalse(response.getBody().isEmpty());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomersSortedByName() {
        when(customerService.getAllCustomersSortedByName()).thenReturn(ResponseEntity.ok(List.of(customer)));

        ResponseEntity<List<Customer>> response = customerController.getCustomersSortedByName();

        assertFalse(response.getBody().isEmpty());
        verify(customerService, times(1)).getAllCustomersSortedByName();
    }

    @Test
    void testGetCustomersSortedByEmail() {
        when(customerService.getAllCustomersSortedByEmail()).thenReturn(ResponseEntity.ok(List.of(customer)));

        ResponseEntity<List<Customer>> response = customerController.getCustomersSortedByEmail();

        assertFalse(response.getBody().isEmpty());
        verify(customerService, times(1)).getAllCustomersSortedByEmail();
    }

    @Test
    void testDeleteCustomer() {
        when(customerService.deleteCustomer(1L)).thenReturn(ResponseEntity.ok("Customer deleted"));

        ResponseEntity<String> response = customerController.deleteCustomer(1L);

        assertEquals("Customer deleted", response.getBody());
        verify(customerService, times(1)).deleteCustomer(1L);
    }
}
