package com.example.shop_spring.Repositorys;

import com.example.shop_spring.Entitys.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
