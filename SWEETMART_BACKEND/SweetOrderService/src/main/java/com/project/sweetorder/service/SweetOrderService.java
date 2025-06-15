package com.project.sweetorder.service;

import com.project.sweetorder.feign.CartFeignClient;
import com.project.sweetorder.model.CartItemDTO;
import com.project.sweetorder.model.OrderItem;
import com.project.sweetorder.model.SweetOrder;
import com.project.sweetorder.repository.SweetOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SweetOrderService {

    @Autowired
    private SweetOrderRepository orderRepo;

    @Autowired
    private CartFeignClient cartClient;

    public ResponseEntity<SweetOrder> placeOrder(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<CartItemDTO> cartItems = cartClient.getCartByUsername(username);
        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        double totalAmount = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemDTO cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(Long.valueOf(cartItem.getProductId()));
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProductPrice());
            totalAmount += cartItem.getProductPrice() * cartItem.getQuantity();
            orderItems.add(orderItem);
        }

        SweetOrder order = new SweetOrder();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        order.setOrderItems(orderItems);

        SweetOrder savedOrder = orderRepo.save(order);
        cartClient.clearCart(username);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    public ResponseEntity<List<SweetOrder>> getOrders(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<SweetOrder> orders = orderRepo.findByUsername(username);
        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(orders);
        }
        return ResponseEntity.ok(orders);
    }

    public ResponseEntity<List<SweetOrder>> updateOrders(String username, SweetOrder updatedOrderData) {
        if (username == null || username.trim().isEmpty() || updatedOrderData == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<SweetOrder> userOrders = orderRepo.findByUsername(username);
        if (userOrders == null || userOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        for (SweetOrder order : userOrders) {
            order.setTotalAmount(updatedOrderData.getTotalAmount());
            order.setOrderDate(LocalDateTime.now());
        }
        List<SweetOrder> updated = orderRepo.saveAll(userOrders);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<String> deleteOrdersByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required");
        }
        List<SweetOrder> userOrders = orderRepo.findByUsername(username);
        if (userOrders == null || userOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for user: " + username);
        }
        orderRepo.deleteAll(userOrders);
        return ResponseEntity.ok("Orders deleted for user: " + username);
    }

    public ResponseEntity<List<SweetOrder>> getAllOrders() {
        List<SweetOrder> orders = orderRepo.findAll();
        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(orders);
        }
        return ResponseEntity.ok(orders);
    }
}
