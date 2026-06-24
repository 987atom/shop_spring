package com.example.shop_spring.app.controllers;

import com.example.shop_spring.app.controllers.Responses.ProductCartResponse;
import com.example.shop_spring.app.entitys.CartEntity;
import com.example.shop_spring.app.entitys.ProductCartEntity;
import com.example.shop_spring.app.entitys.ProductEntity;
import com.example.shop_spring.app.services.CartService;
import com.example.shop_spring.app.services.ProductCartService;
import com.example.shop_spring.app.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productCart")
public class ProductCartController {
    private final ProductCartService productCartService;
    private final ProductService productService;
    private final CartService cartService;

    public ProductCartController (ProductCartService productCartService,
                                  ProductService productService,
                                  CartService cartService) {
        this.productCartService = productCartService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/get")
    public List<ProductCartResponse> getAll() {
        List<ProductCartResponse> responseList = new ArrayList<>();

        for (ProductCartEntity cart : productCartService.findeAll()) {
            if (cart != null) {
                responseList.add(getByID(cart.getId()));
            }
        }

        return responseList;
    }

    @GetMapping("/get/{id}")
    public ProductCartResponse getByID(@PathVariable Long id) {
        ProductCartEntity cart = productCartService.findeByID(id);
        ProductCartResponse response = new ProductCartResponse();
        response.setId(cart.getId());
        response.setProducts(cart.getProducts().getName());
        response.setCarts(cart.getCarts().getNumber());

        return response;
    }


//    @PostMapping("/post")
//    public ProductCartEntity post(@RequestParam Long idProduct, @RequestParam Long idCart) {
    @PostMapping("/post/{idProduct}/{idCart}")
    public ProductCartResponse post(@PathVariable Long idProduct, @PathVariable Long idCart) {
        ProductEntity product = productService.findeByID(idProduct);
        CartEntity cart = cartService.findeByID(idCart);

        ProductCartEntity productCart = new ProductCartEntity(product, cart);

        productCartService.save(productCart);

        return getByID(productCart.getId());
    }

    @PutMapping("/put/{id}/{idProduct}/{idCart}")
    public ProductCartEntity put(@PathVariable Long id, @PathVariable Long idProduct, @PathVariable Long idCart) {
        ProductEntity product = productService.findeByID(idProduct);
        CartEntity cart = cartService.findeByID(idCart);

        ProductCartEntity productCart = new ProductCartEntity(product, cart);

        return productCartService.update(id, productCart);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        try {
            productCartService.deleteByID(id);
        } catch (Exception e) {}
    }
}
