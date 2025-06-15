package com.project.sweetorder.controller;

import com.project.sweetorder.model.SweetOrder;
import com.project.sweetorder.service.SweetOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SweetOrderController {

    private final SweetOrderService orderService;

    public SweetOrderController(SweetOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/user/place/{username}")
    public ResponseEntity<SweetOrder> placeOrder(@PathVariable String username) {
        return orderService.placeOrder(username);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<SweetOrder>> getOrders(@PathVariable String username) {
        return orderService.getOrders(username);
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<List<SweetOrder>> updateOrders(
            @PathVariable String username,
            @RequestBody SweetOrder updatedOrderData) {
        return orderService.updateOrders(username, updatedOrderData);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<String> deleteOrdersByUsername(@PathVariable String username) {
        return orderService.deleteOrdersByUsername(username);
    }

    @GetMapping("/admin/allorders")
    public ResponseEntity<List<SweetOrder>> getAllOrders() {
        return orderService.getAllOrders();
    }
}
