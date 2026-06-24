package com.example.shop_spring.app.services;

import com.example.shop_spring.app.entitys.CartEntity;
import com.example.shop_spring.app.repositorys.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartEntity save(CartEntity cart) {
        return cartRepository.save(cart);
    }

    public CartEntity findeByID(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public List<CartEntity> findeAll() {
        return cartRepository.findAll();
    }

    public void deleteByID(Long id) {
        cartRepository.deleteById(id);
    }

    public CartEntity update(Long id, CartEntity updateCart) {
        CartEntity cart = cartRepository.findById(id).orElse(null);

        if (cart != null) {
                cart.setUsers(updateCart.getUsers());
                cart.setNumber(updateCart.getNumber());
                cart.setCoast(updateCart.getCoast());
                cart.setCart(updateCart.getCart());
        }

        return cartRepository.save(cart);
    }
}
