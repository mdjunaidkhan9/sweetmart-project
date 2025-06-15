package com.project.category.entity;


import java.util.List;

public class CategoryWithProductsDTO {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private List<ProductDTO> products;
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public CategoryWithProductsDTO(int categoryId, String categoryName, String categoryDescription,
			List<ProductDTO> products) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.products = products;
	}
	public CategoryWithProductsDTO() {
		super();
	}
    
    // Getters and Setters
    // ... (generate all getters and setters)
}

