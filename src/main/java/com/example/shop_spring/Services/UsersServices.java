package com.example.shop_spring.Services;

import com.example.shop_spring.Entitys.UsersEntity;
import com.example.shop_spring.Repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop_spring.domain.events.user.UserCreatedEvent;
import com.example.shop_spring.domain.events.user.UserDeletedEvent;
import com.example.shop_spring.domain.events.user.UserUpdatedEvent;
import com.example.shop_spring.infrastructure.messaging.publisher.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Сервис для работы с пользователями.
 *
 * Важно: сначала сохраняем в БД, потом отправляем события.
 * Если сначала отправить событие, а потом БД упадет - у нас будет ложное событие.
 */
//@RequiredArgsConstructor
//@Slf4j
@Service
public class UsersServices {
    @Autowired
    private UsersRepository usersRepository;

    public UsersEntity save(UsersEntity usersEntity) {
        return usersRepository.save(usersEntity);
    }

    public UsersEntity findeByID(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public List<UsersEntity> findeAll() {
        return usersRepository.findAll();
    }

    public void deleteByID(Long id) {
        usersRepository.deleteById(id);
    }

    public UsersEntity update(Long id, UsersEntity updateUsers) {
        UsersEntity usersEntity = usersRepository.findById(id).orElse(null);

        if (usersEntity != null) {
            usersEntity.setCarts(updateUsers.getCarts());
            usersEntity.setName(updateUsers.getName());
            usersEntity.setRole(updateUsers.getRole());
            usersEntity.setSurename(updateUsers.getSurename());
        }

        return usersRepository.save(usersEntity);
    }
}
