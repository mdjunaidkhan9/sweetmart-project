package com.project.product.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.product.model.CategoryEntityDTO;
import com.project.product.model.ProductEntity;
import com.project.product.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new ProductEntity(1, "Chocolate", 10.0, "Cocoa treat", true, "Sweets");
    }

    @Test
    void testGetAllCategories() {
        when(productService.fetchAllCategories()).thenReturn(List.of(new CategoryEntityDTO()));
        List<CategoryEntityDTO> result = productController.getall();
        assertEquals(1, result.size());
    }

    @Test
    void testAddProduct() {
        when(productService.addProduct(any(ProductEntity.class))).thenReturn(ResponseEntity.ok(product));
        ResponseEntity<ProductEntity> response = productController.addproduct(product);
        assertEquals("Chocolate", response.getBody().getProductName());
    }

    @Test
    void testAllProducts() {
        when(productService.allProducts()).thenReturn(List.of(product));
        List<ProductEntity> result = productController.allproduct();
        assertEquals(1, result.size());
    }

    @Test
    void testGetProductByName() {
        when(productService.getProductByName("Chocolate")).thenReturn(product);
        ProductEntity result = productController.productbyname("Chocolate");
        assertEquals("Chocolate", result.getProductName());
    }

    @Test
    void testAllProductSortByPrice() {
        when(productService.findAllBySort()).thenReturn(List.of(product));
        List<ProductEntity> result = productController.allproductsort();
        assertEquals(1, result.size());
    }

    @Test
    void testAllProductSortByName() {
        when(productService.findAllBySortByName()).thenReturn(List.of(product));
        List<ProductEntity> result = productController.allproductsortbyname();
        assertEquals(1, result.size());
    }

    @Test
    void testProductByCategory() {
        when(productService.getProductsByCategory("Sweets")).thenReturn(List.of(product));
        List<ProductEntity> result = productController.productbycategory("Sweets");
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateEntity(eq(1), any(ProductEntity.class))).thenReturn(ResponseEntity.ok(product));
        ResponseEntity<ProductEntity> response = productController.updateuser(1, product);
        assertEquals("Chocolate", response.getBody().getProductName());
    }

    @Test
    void testDeleteProduct() {
        when(productService.deleteProduct(1)).thenReturn(ResponseEntity.ok("Deleted"));
        ResponseEntity<String> response = productController.deleteuser(1);
        assertEquals("Deleted", response.getBody());
    }
}
