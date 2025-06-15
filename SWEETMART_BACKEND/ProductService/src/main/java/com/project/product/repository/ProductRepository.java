package com.project.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.product.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
	Optional<ProductEntity> findByProductName(String productName);
	List<ProductEntity> findByProductCategory(String productCategory);

}
