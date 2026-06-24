package com.example.shop_spring.app.repositorys;

import com.example.shop_spring.app.entitys.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
