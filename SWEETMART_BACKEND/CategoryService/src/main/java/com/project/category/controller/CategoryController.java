// CategoryController.java
package com.project.category.controller;

import com.project.category.entity.CategoryEntity;
import com.project.category.entity.CategoryWithProductsDTO;
import com.project.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/add")
    public ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryEntity category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable int id, @RequestBody CategoryEntity updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/all-with-products")
    public ResponseEntity<List<CategoryWithProductsDTO>> getAllCategoriesWithProducts() {
        return categoryService.getAllCategoriesWithProducts();
    }
}