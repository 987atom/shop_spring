package com.example.shop_spring.app.entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surename;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "users")
    private List<CartEntity> cart;

    public List<CartEntity> getCarts() {
        return cart;
    }

    public void setCarts(List<CartEntity> carts) {
        this.cart = carts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UsersEntity(String name, String surename, String role, List<CartEntity> cart) {
        this.name = name;
        this.surename = surename;
        this.role = role;
        this.cart = cart;
    }

    public UsersEntity() {
    }
}
