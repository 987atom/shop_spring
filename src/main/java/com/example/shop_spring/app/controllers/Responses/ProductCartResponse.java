package com.example.shop_spring.app.controllers.Responses;

public class ProductCartResponse {

    private Long id;
    private String products;
    private String carts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getCarts() {
        return carts;
    }

    public void setCarts(String carts) {
        this.carts = carts;
    }

    public ProductCartResponse(String products, String carts) {
        this.products = products;
        this.carts = carts;
    }

    public ProductCartResponse() {
    }
}
