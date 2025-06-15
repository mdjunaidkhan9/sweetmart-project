package com.project.sweetorder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sweet_items")
public class SweetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sweetItemId;

    private Long productId;

    private String productName;

    private Integer quantity;

    private Double price;

    // Getters and Setters

    public Long getSweetItemId() {
        return sweetItemId;
    }

    public void setSweetItemId(Long sweetItemId) {
        this.sweetItemId = sweetItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
