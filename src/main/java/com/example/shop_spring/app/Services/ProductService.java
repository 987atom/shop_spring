package com.example.shop_spring.app.Services;

import com.example.shop_spring.app.Entitys.ProductEntity;
import com.example.shop_spring.app.Repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public ProductEntity findeByID(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<ProductEntity> findeAll() {
        return productRepository.findAll();
    }

    public void deleteByID(Long id) {
        productRepository.deleteById(id);
    }

    public ProductEntity update(Long id, ProductEntity updateProduct) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if (productEntity != null) {
            productEntity.setProduct(updateProduct.getProduct());
            productEntity.setCoast(updateProduct.getCoast());
            productEntity.setName(updateProduct.getName());
            productEntity.setCount(updateProduct.getCount());
        }

        //сохраняем дополнительно помимо не измененной версии?
        return productRepository.save(productEntity);
    }
}
