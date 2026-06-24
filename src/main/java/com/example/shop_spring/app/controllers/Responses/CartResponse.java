package com.example.shop_spring.app.controllers.Responses;

import com.example.shop_spring.app.entitys.ProductCartEntity;
import com.example.shop_spring.app.entitys.UsersEntity;

import java.util.List;

public class CartResponse {
    Long id;
    UsersEntity users;
    String number;
    int coast;
    List<ProductCartEntity> cart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
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

    public List<ProductCartEntity> getCart() {
        return cart;
    }

    public void setCart(List<ProductCartEntity> cart) {
        this.cart = cart;
    }

    public CartResponse(UsersEntity users, String number, int coast, List<ProductCartEntity> cart) {
        this.users = users;
        this.number = number;
        this.coast = coast;
        this.cart = cart;
    }

    public CartResponse() {
    }
}
