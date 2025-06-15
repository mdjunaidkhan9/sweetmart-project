package com.project.cart.controller;

import com.project.cart.model.CartItem;
import com.project.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    /** Add an item to the cart */
    @PostMapping("/user/add/{username}/{productName}")
    public ResponseEntity<CartItem> addToCart(
            @PathVariable String username,
            @PathVariable String productName,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        return cartService.addToCart(username, productName, quantity);
    }

    /** Get cart items for a user */
    @GetMapping("/user/{username}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable String username) {
        return cartService.getCartByUsername(username);
    }

    /** Update quantity of a cart item */
    @PutMapping("/user/update/{username}/{productName}")
    public ResponseEntity<CartItem> updateQuantity(
            @PathVariable String username,
            @PathVariable String productName,
            @RequestParam int quantity
    ) {
        return cartService.updateQuantity(username, productName, quantity);
    }

    /** Remove a cart item */
    @DeleteMapping("/user/remove/{username}/{productName}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable String username,
            @PathVariable String productName
    ) {
        return cartService.removeFromCart(username, productName);
    }

    /** Clear all cart items for a user */
    @DeleteMapping("/user/clear/{username}")
    public ResponseEntity<String> clearCart(@PathVariable String username) {
        return cartService.clearCart(username);
    }
}
