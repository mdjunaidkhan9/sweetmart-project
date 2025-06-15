package com.project.orderbill.dto;
import java.time.LocalDateTime;
import java.util.List;

public class SweetOrderDTO {
    private Long id;
    private String username;
    private double totalAmount;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime orderDate;

    public SweetOrderDTO() {}

    public SweetOrderDTO(Long id, String username, double totalAmount, List<OrderItemDTO> orderItems, LocalDateTime orderDate) {
        this.id = id;
        this.username = username;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDTO> orderItems) { this.orderItems = orderItems; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}
