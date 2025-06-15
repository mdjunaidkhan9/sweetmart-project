package com.project.customer.controller;

import com.project.customer.model.Customer;
import com.project.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/user/add/{username}")
    public ResponseEntity<Customer> save(@PathVariable String username, @RequestBody Customer customer) {
        return service.saveCustomer(username, customer);
    }

    @GetMapping("/user/get/{username}")
    public ResponseEntity<Customer> get(@PathVariable String username) {
        return service.getCustomer(username);
    }

    @GetMapping("/admin/sortbyname")
    public ResponseEntity<List<Customer>> getCustomersSortedByName() {
        return service.getAllCustomersSortedByName();
    }

    @GetMapping("/admin/sortbyemail") // ✅ New Endpoint
    public ResponseEntity<List<Customer>> getCustomersSortedByEmail() {
        return service.getAllCustomersSortedByEmail();
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return service.getAllCustomers();
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        return service.deleteCustomer(id);
    }
}
