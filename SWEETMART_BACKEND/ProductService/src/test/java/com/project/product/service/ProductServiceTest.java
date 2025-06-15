package com.project.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.product.feign.CategoryClient;
import com.project.product.model.CategoryEntityDTO;
import com.project.product.model.ProductEntity;
import com.project.product.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryClient categoryClient;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new ProductEntity(1, "Chocolate", 10.0, "Cocoa treat", true, "Sweets");
    }

    // ===================== fetchAllCategories =======================

    @Test
    void testFetchAllCategories() {
        when(categoryClient.getAllCategories()).thenReturn(List.of(new CategoryEntityDTO()));
        List<CategoryEntityDTO> result = productService.fetchAllCategories();
        assertEquals(1, result.size());
    }

    @Test
    void testFetchAllCategories_Empty() {
        when(categoryClient.getAllCategories()).thenReturn(Collections.emptyList());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.fetchAllCategories());
        assertEquals("No categories found.", exception.getMessage());
    }

    // ===================== addProduct =======================

    @Test
    void testAddProduct_Success() {
        when(productRepository.save(any())).thenReturn(product);
        ResponseEntity<ProductEntity> response = productService.addProduct(product);
        assertEquals("Chocolate", response.getBody().getProductName());
    }

    @Test
    void testAddProduct_Invalid() {
        product.setProductCategory(null);
        ResponseEntity<ProductEntity> response = productService.addProduct(product);
        assertEquals(400, response.getStatusCodeValue());
    }

    // ===================== allProducts =======================

    @Test
    void testAllProducts_Success() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<ProductEntity> result = productService.allProducts();
        assertEquals(1, result.size());
    }

    @Test
    void testAllProducts_Empty() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.allProducts());
        assertEquals("No products available.", exception.getMessage());
    }

    // ===================== getProductByName =======================

    @Test
    void testGetProductByName_Found() {
        when(productRepository.findByProductName("Chocolate")).thenReturn(Optional.of(product));
        ProductEntity result = productService.getProductByName("Chocolate");
        assertEquals(1, result.getProductId());
    }

    @Test
    void testGetProductByName_NotFound() {
        when(productRepository.findByProductName("Vanilla")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductByName("Vanilla"));
        assertEquals("Product not found with name: Vanilla", exception.getMessage());
    }

    // ===================== findAllBySort =======================

    @Test
    void testFindAllBySort_Success() {
        when(productRepository.findAll(Sort.by("productPrice"))).thenReturn(List.of(product));
        List<ProductEntity> result = productService.findAllBySort();
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllBySort_Empty() {
        when(productRepository.findAll(Sort.by("productPrice"))).thenReturn(Collections.emptyList());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.findAllBySort());
        assertEquals("No products available to sort.", exception.getMessage());
    }

    // ===================== findAllBySortByName =======================

    @Test
    void testFindAllBySortByName_Success() {
        when(productRepository.findAll(Sort.by("productName"))).thenReturn(List.of(product));
        List<ProductEntity> result = productService.findAllBySortByName();
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllBySortByName_Empty() {
        when(productRepository.findAll(Sort.by("productName"))).thenReturn(Collections.emptyList());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.findAllBySortByName());
        assertEquals("No products available to sort.", exception.getMessage());
    }

    // ===================== getProductsByCategory =======================

    @Test
    void testGetProductsByCategory_Success() {
        when(productRepository.findByProductCategory("Sweets")).thenReturn(List.of(product));
        List<ProductEntity> result = productService.getProductsByCategory("Sweets");
        assertEquals(1, result.size());
    }

    @Test
    void testGetProductsByCategory_Empty() {
        when(productRepository.findByProductCategory("Toys")).thenReturn(Collections.emptyList());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductsByCategory("Toys"));
        assertEquals("No products found in category: Toys", exception.getMessage());
    }

    // ===================== updateEntity =======================

    @Test
    void testUpdateEntity_Found() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        product.setProductName("Updated");
        ResponseEntity<ProductEntity> response = productService.updateEntity(1, product);
        assertEquals("Updated", response.getBody().getProductName());
    }

    @Test
    void testUpdateEntity_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<ProductEntity> response = productService.updateEntity(1, product);
        assertEquals(404, response.getStatusCodeValue());
    }

    // ===================== deleteProduct =======================

    @Test
    void testDeleteProduct_Found() {
        when(productRepository.existsById(1)).thenReturn(true);
        ResponseEntity<String> response = productService.deleteProduct(1);
        assertTrue(response.getBody().contains("deleted"));
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.existsById(1)).thenReturn(false);
        ResponseEntity<String> response = productService.deleteProduct(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}
