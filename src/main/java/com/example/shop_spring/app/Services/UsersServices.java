//package com.example.shop_spring.app.Services;
//
//import com.example.shop_spring.app.Entitys.UsersEntity;
//import com.example.shop_spring.app.Repositorys.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UsersServices {
//    @Autowired
//    private UsersRepository usersRepository;
//
//    public UsersEntity save(UsersEntity usersEntity) {
//        return usersRepository.save(usersEntity);
//    }
//
//    public UsersEntity findeByID(Long id) {
//        return usersRepository.findById(id).orElse(null);
//    }
//
//    public List<UsersEntity> findeAll() {
//        return usersRepository.findAll();
//    }
//
//    public void deleteByID(Long id) {
//        usersRepository.deleteById(id);
//    }
//
//    public UsersEntity update(Long id, UsersEntity updateUsers) {
//        UsersEntity usersEntity = usersRepository.findById(id).orElse(null);
//
//        if (usersEntity != null) {
//            usersEntity.setCarts(updateUsers.getCarts());
//            usersEntity.setName(updateUsers.getName());
//            usersEntity.setRole(updateUsers.getRole());
//            usersEntity.setSurename(updateUsers.getSurename());
//        }
//
//        return usersRepository.save(usersEntity);
//    }
//}



package com.example.shop_spring.app.Services;

import com.example.shop_spring.app.Entitys.UsersEntity;
import com.example.shop_spring.app.Repositorys.UsersRepository;
import com.example.shop_spring.kafka.Services.Producer.UsersEventProducter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsersServices {
    //    @Autowired
    private UsersRepository usersRepository;
    UsersEventProducter eventProducer;
    public UsersServices(UsersRepository usersRepository,
                         UsersEventProducter eventProducer) {
        this.usersRepository = usersRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public UsersEntity save(UsersEntity usersEntity) {
//        return usersRepository.save(usersEntity);


        // 1. Сохраняем в базу
        UsersEntity savedUser = usersRepository.save(usersEntity);

        // 2. Отправляем событие (после сохранения)
        try {
            eventProducer.sendUsersCreated(savedUser);
        } catch (Exception e) {
            // Логируем ошибку, но не откатываем транзакцию
            // Возможно, нужно сохранить в отдельную таблицу для ретрая
            log.error("Failed to send Kafka event for book: {}", savedUser.getId());
        }

        return savedUser;
    }

    public UsersEntity findeByID(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public List<UsersEntity> findeAll() {
        return usersRepository.findAll();
    }

    @Transactional
    public void deleteByID(Long id) {
        UsersEntity usersEntity = usersRepository.findById(id).orElse(null);

        // Отправляем событие перед удалением
        try {
            //TODO сделать удаление
//            eventProducer.sendUsersDeleted(usersEntity);
        } catch (Exception e) {
//            log.error("Failed to send Kafka event for book deletion: {}", id);
        }

        usersRepository.deleteById(id);
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

        UsersEntity savedUser = usersRepository.save(usersEntity);

        try {
            //TODO сделать отправку для UPDATE
//            eventProducer.sendUsersUpdated(savedUser);
        } catch (Exception e) {
//            log.error("Failed to send Kafka event for book update: {}", savedUser.getId());
        }

        return savedUser;
    }
}
