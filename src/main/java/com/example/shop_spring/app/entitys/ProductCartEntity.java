package com.example.shop_spring.app.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "product_cart")
public class ProductCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductEntity products;

    @ManyToOne
    private CartEntity carts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProducts() {
        return products;
    }

    public void setProducts(ProductEntity products) {
        this.products = products;
    }

    public CartEntity getCarts() {
        return carts;
    }

    public void setCarts(CartEntity carts) {
        this.carts = carts;
    }

    public ProductCartEntity() {
    }

    public ProductCartEntity(ProductEntity products, CartEntity carts) {
        this.products = products;
        this.carts = carts;
    }
}
