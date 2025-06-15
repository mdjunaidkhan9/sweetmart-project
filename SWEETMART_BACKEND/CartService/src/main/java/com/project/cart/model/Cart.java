package com.project.cart.model;

import jakarta.persistence.*;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private String username;  // Username from auth token - identifies customer

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")  // foreign key in CartItem table
    private List<CartItem> items = new ArrayList<>();

    public Cart() {}

    // getters & setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}

