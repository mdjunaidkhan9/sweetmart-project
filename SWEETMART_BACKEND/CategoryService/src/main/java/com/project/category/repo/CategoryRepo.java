package com.project.category.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.category.entity.CategoryEntity;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {
}

