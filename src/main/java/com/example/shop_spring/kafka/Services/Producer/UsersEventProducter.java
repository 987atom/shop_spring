package com.example.shop_spring.kafka.Services.Producer;

import com.example.shop_spring.app.Entitys.UsersEntity;
import com.example.shop_spring.kafka.Events.UsersEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsersEventProducter {
    private static final Logger logger = LoggerFactory.getLogger(UsersEventProducter.class);
    private static final String TOPIC = "users";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public UsersEventProducter(KafkaTemplate<String, String> kafkaTemplate,
                               ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    // Метод для отправки события
    public void sendUsersEvent (UsersEvent event) {
        try {
            // Сериализуем объект в JSON
            String jsonMessage = objectMapper.writeValueAsString(event);

            // Отправляем с ключом = bookId для группировки
            String key = String.valueOf(event.getUserID());

            kafkaTemplate.send(TOPIC, key, jsonMessage).whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("Book event sent: {}", event.getEventType());
                        } else {
                            logger.error("Failed to send book event: {}", ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            logger.error("Error serializing book event: {}", e.getMessage());
        }
    }

    // Вспомогательный метод для создания события
    public void sendUsersCreated(UsersEntity user) {
        UsersEvent event = new UsersEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventType("USER_CREATED");
        event.setUserID(user.getId());
        event.setOperation("CREATE");
        event.setName(user.getName());
        event.setSurename(user.getSurename());
        event.setRole(user.getRole());
        event.setTimestamp(LocalDateTime.now());
        event.setSourceService("users-service");

        sendUsersEvent(event);
    }
}
