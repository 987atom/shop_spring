package com.example.shop_spring.Repositorys;

import com.example.shop_spring.Entitys.ProductCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<ProductCartEntity, Long> {
    Long countById(Long Id);
    long count();
}
