package com.example.shop_spring.Entitys;

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
}
