package com.project.orderbill.dto;
public class CustomerDTO {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String shippingAddress;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String username, String name, String email, String phone, String shippingAddress) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.shippingAddress = shippingAddress;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}
