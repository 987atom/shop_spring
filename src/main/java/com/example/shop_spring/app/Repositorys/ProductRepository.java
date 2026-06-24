package com.example.shop_spring.app.Repositorys;

import com.example.shop_spring.app.Entitys.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
