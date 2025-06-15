package com.project.category.service;

import com.project.category.entity.*;
import com.project.category.feign.ProductClient;
import com.project.category.repo.CategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo repo;

    @Autowired
    private ProductClient client;

    public ResponseEntity<CategoryEntity> addCategory(CategoryEntity category) {
        CategoryEntity saved = repo.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        List<CategoryEntity> list = repo.findAll();
        if (list.isEmpty()) {
            throw new RuntimeException("No categories available.");
        }
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<CategoryEntity> updateCategory(int id, CategoryEntity updatedCategory) {
        CategoryEntity existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existing.setCategoryName(updatedCategory.getCategoryName());
        existing.setCategoryDescription(updatedCategory.getCategoryDescription());

        return ResponseEntity.ok(repo.save(existing));
    }

    public ResponseEntity<String> deleteCategory(int id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Cannot delete. Category not found with id: " + id);
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Category deleted with id: " + id);
    }

    public ResponseEntity<List<CategoryWithProductsDTO>> getAllCategoriesWithProducts() {
        List<CategoryEntity> categories = repo.findAll();
        if (categories.isEmpty()) {
            throw new RuntimeException("No categories found for product mapping.");
        }

        List<CategoryWithProductsDTO> result = new ArrayList<>();

        for (CategoryEntity category : categories) {
            CategoryWithProductsDTO dto = new CategoryWithProductsDTO();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryName(category.getCategoryName());
            dto.setCategoryDescription(category.getCategoryDescription());

            List<ProductDTO> products = client.getProductsByCategory(category.getCategoryName());
            dto.setProducts(products);

            result.add(dto);
        }

        return ResponseEntity.ok(result);
    }
}
