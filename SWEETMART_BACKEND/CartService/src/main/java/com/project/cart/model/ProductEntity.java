package com.project.cart.model;
public class ProductEntity {
    private int productId;
    private String productName;
    private double productPrice;
    private String productDescription;
    private boolean productAvailable;
    private String productCategory;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public boolean isProductAvailable() {
		return productAvailable;
	}
	public void setProductAvailable(boolean productAvailable) {
		this.productAvailable = productAvailable;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

    // Getters and Setters
}
