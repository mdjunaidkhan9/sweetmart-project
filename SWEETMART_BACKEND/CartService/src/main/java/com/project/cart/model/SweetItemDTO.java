package com.project.cart.model;

public class SweetItemDTO {
    private Long sweetItemId;
    private Long productId;
    private Integer quantity;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
