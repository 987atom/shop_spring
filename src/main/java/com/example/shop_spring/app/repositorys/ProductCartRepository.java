package com.example.shop_spring.app.repositorys;

import com.example.shop_spring.app.entitys.ProductCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<ProductCartEntity, Long> {
}
