package com.example.shop_spring.app.Repositorys;

import com.example.shop_spring.app.Entitys.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
}
