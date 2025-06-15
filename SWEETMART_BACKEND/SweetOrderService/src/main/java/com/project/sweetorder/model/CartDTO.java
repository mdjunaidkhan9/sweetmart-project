package com.project.sweetorder.model;



import java.util.List;

public class CartDTO {
    private Long cartId;
    private String username;
    private List<CartItemDTO> items;

    public CartDTO() {}

    // getters and setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }
}
