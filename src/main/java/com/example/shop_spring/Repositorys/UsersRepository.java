package com.example.shop_spring.Repositorys;

import com.example.shop_spring.Entitys.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
}
