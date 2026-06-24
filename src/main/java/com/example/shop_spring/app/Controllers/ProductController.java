package com.example.shop_spring.app.Controllers;

import com.example.shop_spring.app.Entitys.ProductCartEntity;
import com.example.shop_spring.app.Entitys.ProductEntity;
import com.example.shop_spring.app.Services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get")
    public List<ProductEntity> getAll() {
        return productService.findeAll();
    }

    @GetMapping("/get/{id}")
    public ProductEntity getByID(@PathVariable Long id) {
        return productService.findeByID(id);
    }

    @PostMapping("/post")
    public ProductEntity post(@RequestBody ProductEntity productEntity) {
        String name = productEntity.getName();
        int count = productEntity.getCount();
        List<ProductCartEntity> productCart = productEntity.getProduct();
        int coast = productEntity.getCoast();

        ProductEntity product = new ProductEntity(name, count, productCart, coast);

        return productService.save(product);
    }

    @PutMapping("/put")
    public ProductEntity put(@RequestBody ProductEntity productEntity) {
        String name = productEntity.getName();
        int count = productEntity.getCount();
        List<ProductCartEntity> productCart = productEntity.getProduct();
        int coast = productEntity.getCoast();
        Long id = productEntity.getId();

        ProductEntity product = new ProductEntity(name, count, productCart, coast);

        return productService.update(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteByID(id);
    }
}
