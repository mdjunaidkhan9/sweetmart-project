package com.project.sweetorder.model;

public class ProductDTO {

    private Long productId;
    private String productName;
    private String category;
    private String description;
    private String photoPath;
    private double price;
    private boolean available;

    public ProductDTO() {}

    public ProductDTO(Long productId, String productName, double price, boolean available) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.available = available;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; } // ✅ FIXED
    public void setProductName(String productName) { this.productName = productName; } // ✅ FIXED

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
