package com.project.payment.dto;

public class BillDTO {
    private Long billId;
    private Long sweetOrderId;
    private String username;
    private String name;
    private String shippingAddress;
    private double totalAmount;

    public BillDTO() {}

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getSweetOrderId() {
        return sweetOrderId;
    }

    public void setSweetOrderId(Long sweetOrderId) {
        this.sweetOrderId = sweetOrderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
