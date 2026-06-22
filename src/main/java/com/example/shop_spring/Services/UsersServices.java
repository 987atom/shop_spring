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
@RequiredArgsConstructor
@Slf4j
@Service
public class UsersServices {
    @Autowired
    private UsersRepository usersRepository;
    private final UserEventPublisher eventPublisher;

    /**
     * Создание нового пользователя.
     *
     * Порядок операций:
     * 1. Сохраняем в БД (получаем ID)
     * 2. Создаем событие
     * 3. Публикуем событие в Kafka
     *
     * Если Kafka недоступна - мы все равно сохранили пользователя,
     * но потеряли событие. Это компромисс для начала.
     * В будущем можно использовать паттерн Outbox для гарантированной доставки.
     */
    @Transactional
    public UsersEntity save(UsersEntity usersEntity) {
        // Сохранение
        UsersEntity userSaved = usersRepository.save(usersEntity);
        log.info("✅ Пользователь сохранен в БД: id={}, name={}",
                userSaved.getId(), userSaved.getName());

        //СОбытие
        UserCreatedEvent event = new UserCreatedEvent(
                userSaved.getId(),
                userSaved.getName(),
                userSaved.getSurename(),
                userSaved.getRole(),
                userSaved.getCarts()
        );

        // Публикация событие в Kafka (асинхронно, не блокируя)
        eventPublisher.publishUserCreated(event);


        return userSaved;
    }

    public UsersEntity findeByID(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public List<UsersEntity> findeAll() {
        return usersRepository.findAll();
    }

    @Transactional
    public void deleteByID(Long id) {
        usersRepository.deleteById(id);
        log.info("✅ Пользователь удален из БД: id={}", id);

        //Публикация события
        UserDeletedEvent event = new UserDeletedEvent(id);
        eventPublisher.publishUserDeleted(event);
    }

    @Transactional
    public UsersEntity update(Long id, UsersEntity updateUsers) {
        UsersEntity usersEntity = usersRepository.findById(id).orElse(null);

        if (usersEntity != null) {
            usersEntity.setCarts(updateUsers.getCarts());
            usersEntity.setName(updateUsers.getName());
            usersEntity.setRole(updateUsers.getRole());
            usersEntity.setSurename(updateUsers.getSurename());
        }

        //Сохранение в БД
        UsersEntity newUser = usersRepository.save(usersEntity);

        //Создание и публикация события
        UserUpdatedEvent event = new UserUpdatedEvent(
                newUser.getId(),
                newUser.getName(),
                newUser.getSurename(),
                newUser.getRole(),
                newUser.getCarts()
        );
        eventPublisher.publishUserUpdated(event);

        return newUser;
    }
}
