package com.project.cart.service;

import com.project.cart.feign.ProductFeignClient;
import com.project.cart.model.CartItem;
import com.project.cart.model.ProductEntity;
import com.project.cart.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartRepository;

    @Autowired
    private ProductFeignClient productClient;

    /** Add an item to the cart */
    public ResponseEntity<CartItem> addToCart(String username, String productName, int quantity) {
        if (username == null || username.trim().isEmpty()
                || productName == null || productName.trim().isEmpty()
                || quantity <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CartItem existing = cartRepository.findByUsernameAndProductName(username, productName);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            CartItem saved = cartRepository.save(existing);
            return ResponseEntity.ok(saved);
        }

        ProductEntity product = productClient.getProductByName(productName);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        CartItem newItem = new CartItem();
        newItem.setUsername(username);
        newItem.setProductName(product.getProductName());
        newItem.setProductPrice(product.getProductPrice());
        newItem.setProductId(product.getProductId());
        newItem.setQuantity(quantity);

        CartItem saved = cartRepository.save(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Get cart items for a user */
    public ResponseEntity<List<CartItem>> getCartByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<CartItem> items = cartRepository.findByUsername(username);
        if (items == null || items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(items);
        }
        return ResponseEntity.ok(items);
    }

    /** Update quantity of a cart item */
    public ResponseEntity<CartItem> updateQuantity(String username, String productName, int quantity) {
        if (username == null || username.trim().isEmpty()
                || productName == null || productName.trim().isEmpty()
                || quantity <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        CartItem existing = cartRepository.findByUsernameAndProductName(username, productName);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        existing.setQuantity(quantity);
        CartItem saved = cartRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    /** Remove a cart item */
    public ResponseEntity<String> removeFromCart(String username, String productName) {
        if (username == null || username.trim().isEmpty()
                || productName == null || productName.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input for removeFromCart");
        }
        CartItem existing = cartRepository.findByUsernameAndProductName(username, productName);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found for remove");
        }
        cartRepository.delete(existing);
        return ResponseEntity.ok("Product removed from cart successfully.");
    }

    /** Clear all cart items for a user */
    public ResponseEntity<String> clearCart(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required for clearCart");
        }
        List<CartItem> items = cartRepository.findByUsername(username);
        if (items == null || items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cart already empty");
        }
        cartRepository.deleteAll(items);
        return ResponseEntity.ok("Cart cleared successfully.");
    }
}
