package com.example.shop_spring.app.Repositorys;

import com.example.shop_spring.app.Entitys.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
