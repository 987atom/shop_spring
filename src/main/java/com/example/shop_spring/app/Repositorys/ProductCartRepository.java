package com.example.shop_spring.app.Repositorys;

import com.example.shop_spring.app.Entitys.ProductCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<ProductCartEntity, Long> {
}
