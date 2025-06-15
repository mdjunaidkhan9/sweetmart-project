package com.project.cart.repository;

import com.project.cart.model.Cart;
import com.project.cart.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUsername(String username); // ✅ returns List<CartItem>
    CartItem findByUsernameAndProductName(String username, String productName);
}

