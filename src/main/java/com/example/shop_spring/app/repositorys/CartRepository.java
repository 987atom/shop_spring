package com.example.shop_spring.app.repositorys;

import com.example.shop_spring.app.entitys.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
