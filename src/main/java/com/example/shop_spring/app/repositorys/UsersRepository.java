package com.example.shop_spring.app.repositorys;

import com.example.shop_spring.app.entitys.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
}
