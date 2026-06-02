package com.example.shop_spring.Controllers;

import com.example.shop_spring.Entitys.CartEntity;
import com.example.shop_spring.Entitys.UsersEntity;
import com.example.shop_spring.Services.UsersServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersServices usersServices;

    public UsersController(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @GetMapping("/get")
    public List<UsersEntity> getAll() {
        return usersServices.findeAll();
    }

    @GetMapping("/get/{id}")
    public UsersEntity getByID(@PathVariable Long id) {
        return usersServices.findeByID(id);
    }

    @PostMapping("/post")
    public UsersEntity post(@RequestBody UsersEntity usersEntity) {
        String name = usersEntity.getName();
        String surename = usersEntity.getSurename();
        String role = usersEntity.getRole();
        List<CartEntity> cart = usersEntity.getCarts();

        UsersEntity users = new UsersEntity(name, surename, role, cart);

        return usersServices.save(users);
    }

    @PutMapping("/put")
    public UsersEntity put(@RequestBody UsersEntity usersEntity) {
        String name = usersEntity.getName();
        String surename = usersEntity.getSurename();
        String role = usersEntity.getRole();
        List<CartEntity> cart = usersEntity.getCarts();
        Long id = usersEntity.getId();

        UsersEntity users = new UsersEntity(name, surename, role, cart);

        return usersServices.update(id, users);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        usersServices.deleteByID(id);
    }
}
