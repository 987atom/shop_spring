package com.example.shop_spring.app.Entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UsersEntity users;

    @Column(nullable = false)
    private String number;

    private int coast;

    @OneToMany(mappedBy = "carts")
    private List<ProductCartEntity> cart;

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public List<ProductCartEntity> getCart() {
        return cart;
    }

    public void setCart(List<ProductCartEntity> cart) {
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersEntity getUsers_id() {
        return users;
    }

    public void setUsers_id(UsersEntity users_id) {
        this.users = users_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public UsersEntity getUser() {
        return users;
    }

    public void setUser(UsersEntity user) {
        this.users = user;
    }

    public CartEntity(UsersEntity users, String number, int coast, List<ProductCartEntity> cart) {
        this.users = users;
        this.number = number;
        this.coast = coast;
        this.cart = cart;
    }

    public CartEntity() {
    }
}
