package com.example.shop_spring.Services;

import com.example.shop_spring.Entitys.ProductCartEntity;
import com.example.shop_spring.Repositorys.ProductCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCartService {
    @Autowired
    private ProductCartRepository productCartRepository;

    public ProductCartEntity save(ProductCartEntity productCartEntity) {
        return productCartRepository.save(productCartEntity);
    }

    public ProductCartEntity findeByID(Long id) {
        return productCartRepository.findById(id).orElse(null);
    }

    public List<ProductCartEntity> findeAll() {
        return productCartRepository.findAll();
    }

    public void deleteByID(Long id) {
        productCartRepository.deleteById(id);
    }

    public ProductCartEntity update(Long id, ProductCartEntity updateProductCart) {
        ProductCartEntity productCartEntity = productCartRepository.findById(id).orElse(null);

        if (productCartEntity != null) {
            productCartEntity.setCarts(updateProductCart.getCarts());
            productCartEntity.setProducts(updateProductCart.getProducts());
        }

        return productCartRepository.save(productCartEntity);
    }

    public Long getProductCartItemCount(Long userId) {
        return productCartRepository.countById(userId);
    }

    public Long getTotalItemCount() {
        return productCartRepository.count();
    }
}
