package com.project.category.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.project.category.entity.CategoryEntity;
import com.project.category.repo.CategoryRepo;
import com.project.category.feign.ProductClient;
import com.project.category.entity.CategoryWithProductsDTO;
import com.project.category.entity.ProductDTO;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private ProductClient productClient;

    private CategoryEntity category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new CategoryEntity();
        category.setCategoryId(1);
        category.setCategoryName("Sweets");
        category.setCategoryDescription("Delicious sweet items.");
    }

    @Test
    void testAddCategory() {
        when(categoryRepo.save(any(CategoryEntity.class))).thenReturn(category);

        ResponseEntity<CategoryEntity> response = categoryService.addCategory(category);

        assertNotNull(response.getBody());
        assertEquals("Sweets", response.getBody().getCategoryName());
        verify(categoryRepo, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepo.findAll()).thenReturn(List.of(category));

        ResponseEntity<List<CategoryEntity>> response = categoryService.getAllCategories();

        assertEquals(1, response.getBody().size());
        verify(categoryRepo, times(1)).findAll();
    }

    @Test
    void testGetAllCategories_Empty() {
        when(categoryRepo.findAll()).thenReturn(List.of());

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.getAllCategories());

        assertEquals("No categories available.", exception.getMessage());
        verify(categoryRepo, times(1)).findAll();
    }

    @Test
    void testUpdateCategory_Success() {
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(categoryRepo.save(any(CategoryEntity.class))).thenReturn(category);

        ResponseEntity<CategoryEntity> response = categoryService.updateCategory(1, category);

        assertNotNull(response.getBody());
        assertEquals("Sweets", response.getBody().getCategoryName());
        verify(categoryRepo, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    void testDeleteCategory_Success() {
        when(categoryRepo.existsById(1)).thenReturn(true);
        doNothing().when(categoryRepo).deleteById(1);

        ResponseEntity<String> response = categoryService.deleteCategory(1);

        assertEquals("Category deleted with id: 1", response.getBody());
        verify(categoryRepo, times(1)).deleteById(1);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepo.existsById(99)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.deleteCategory(99));

        assertEquals("Cannot delete. Category not found with id: 99", exception.getMessage());
        verify(categoryRepo, times(1)).existsById(99);
    }

    @Test
    void testGetAllCategoriesWithProducts_Empty() {
        when(categoryRepo.findAll()).thenReturn(List.of());

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.getAllCategoriesWithProducts());

        assertEquals("No categories found for product mapping.", exception.getMessage());
        verify(categoryRepo, times(1)).findAll();
    }

    @Test
    void testGetAllCategoriesWithProducts_Success() {
        when(categoryRepo.findAll()).thenReturn(List.of(category));
        when(productClient.getProductsByCategory("Sweets")).thenReturn(List.of(new ProductDTO()));

        ResponseEntity<List<CategoryWithProductsDTO>> response = categoryService.getAllCategoriesWithProducts();

        assertFalse(response.getBody().isEmpty());
        verify(categoryRepo, times(1)).findAll();
        verify(productClient, times(1)).getProductsByCategory("Sweets");
    }
}
