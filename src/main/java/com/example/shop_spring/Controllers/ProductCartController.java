package com.example.shop_spring.Controllers;

import com.example.shop_spring.Controllers.Responses.ProductCartResponse;
import com.example.shop_spring.Entitys.CartEntity;
import com.example.shop_spring.Entitys.ProductCartEntity;
import com.example.shop_spring.Entitys.ProductEntity;
import com.example.shop_spring.Services.CartService;
import com.example.shop_spring.Services.ProductCartService;
import com.example.shop_spring.Services.ProductService;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping("/get")
//    public List<ProductCartEntity> getAll() {
//        return productCartService.findeAll();
//    }
    @GetMapping("/get")
    public List<ProductCartResponse> getAll() {

        List<ProductCartResponse> responseList = new ArrayList<>();

        for (ProductCartEntity cart : productCartService.findeAll()) {
//            ProductCartEntity cart = productCartService.findeByID(i);
            if (cart != null) {
                ProductCartResponse response = new ProductCartResponse();
                response.setId(cart.getId());
                response.setProducts(cart.getProducts().getName());
                response.setCarts(cart.getCarts().getNumber());

                responseList.add(response);
            }
        }

        return responseList;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductCartResponse> getByID(@PathVariable Long id) {
        ProductCartEntity cart = productCartService.findeByID(id);
        ProductCartResponse response = new ProductCartResponse();
        response.setId(cart.getId());
        response.setProducts(cart.getProducts().getName());
        response.setCarts(cart.getCarts().getNumber());

        return ResponseEntity.ok(response);
    }


//    @PostMapping("/post")
//    public ProductCartEntity post(@RequestParam Long idProduct, @RequestParam Long idCart) {
    @PostMapping("/post/{idProduct}/{idCart}")
    public ProductCartEntity post(@PathVariable Long idProduct, @PathVariable Long idCart) {
        ProductEntity product = productService.findeByID(idProduct);
        CartEntity cart = cartService.findeByID(idCart);

        ProductCartEntity productCart = new ProductCartEntity(product, cart);

        return productCartService.save(productCart);
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
