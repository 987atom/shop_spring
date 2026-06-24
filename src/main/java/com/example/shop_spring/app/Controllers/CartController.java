package com.example.shop_spring.app.Controllers;

import com.example.shop_spring.app.Entitys.CartEntity;
import com.example.shop_spring.app.Entitys.ProductCartEntity;
import com.example.shop_spring.app.Entitys.UsersEntity;
import com.example.shop_spring.app.Services.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/get")
    public List<CartEntity> getAll() {
        return cartService.findeAll();
    }

    @GetMapping("/get/{id}")
    public CartEntity getByID(@PathVariable Long id) {
        return cartService.findeByID(id);
    }

    @PostMapping("/post")
    public CartEntity post(@RequestBody CartEntity cartEntity) {
        List<ProductCartEntity> productCart = cartEntity.getCart();
        int coast = cartEntity.getCoast();
        String number = cartEntity.getNumber();
        UsersEntity users = cartEntity.getUser();

        CartEntity cart = new CartEntity(users, number, coast, productCart);

        return cartService.save(cart);
    }

    @PutMapping("/put")
    public CartEntity put(@RequestBody CartEntity cartEntity) {
        List<ProductCartEntity> productCart = cartEntity.getCart();
        int coast = cartEntity.getCoast();
        String number = cartEntity.getNumber();
        UsersEntity users = cartEntity.getUser();
        Long id = cartEntity.getId();

        CartEntity cart = new CartEntity(users, number, coast, productCart);

        return cartService.update(id, cart);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        try {
            cartService.deleteByID(id);
        } catch (Exception e) {}
    }
}
