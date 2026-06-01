package com.example.shop_spring.Repositorys;

import com.example.shop_spring.Entitys.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
