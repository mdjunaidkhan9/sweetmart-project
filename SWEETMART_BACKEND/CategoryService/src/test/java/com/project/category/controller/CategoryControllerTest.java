package com.project.category.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.project.category.entity.CategoryEntity;
import com.project.category.service.CategoryService;
import com.project.category.entity.CategoryWithProductsDTO;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

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
        when(categoryService.addCategory(any(CategoryEntity.class))).thenReturn(ResponseEntity.ok(category));

        ResponseEntity<CategoryEntity> response = categoryController.addCategory(category);

        assertNotNull(response.getBody());
        assertEquals("Sweets", response.getBody().getCategoryName());
        verify(categoryService, times(1)).addCategory(any(CategoryEntity.class));
    }

    @Test
    void testGetAllCategories() {
        when(categoryService.getAllCategories()).thenReturn(ResponseEntity.ok(List.of(category)));

        ResponseEntity<List<CategoryEntity>> response = categoryController.getAllCategories();

        assertEquals(1, response.getBody().size());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testUpdateCategory() {
        when(categoryService.updateCategory(eq(1), any(CategoryEntity.class))).thenReturn(ResponseEntity.ok(category));

        ResponseEntity<CategoryEntity> response = categoryController.updateCategory(1, category);

        assertNotNull(response.getBody());
        assertEquals("Sweets", response.getBody().getCategoryName());
        verify(categoryService, times(1)).updateCategory(eq(1), any(CategoryEntity.class));
    }

    @Test
    void testDeleteCategory() {
        when(categoryService.deleteCategory(1)).thenReturn(ResponseEntity.ok("Category deleted"));

        ResponseEntity<String> response = categoryController.deleteCategory(1);

        assertEquals("Category deleted", response.getBody());
        verify(categoryService, times(1)).deleteCategory(1);
    }

    @Test
    void testGetAllCategoriesWithProducts() {
        when(categoryService.getAllCategoriesWithProducts()).thenReturn(ResponseEntity.ok(List.of(new CategoryWithProductsDTO())));

        ResponseEntity<List<CategoryWithProductsDTO>> response = categoryController.getAllCategoriesWithProducts();

        assertFalse(response.getBody().isEmpty());
        verify(categoryService, times(1)).getAllCategoriesWithProducts();
    }
}
