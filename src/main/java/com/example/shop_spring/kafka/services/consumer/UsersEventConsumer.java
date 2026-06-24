package com.example.shop_spring.kafka.services.consumer;

import com.example.shop_spring.kafka.events.UsersEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UsersEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UsersEventConsumer.class);
    private final ObjectMapper objectMapper;
//    private final AuditService auditService; // Сервис для аудита


    public UsersEventConsumer(ObjectMapper objectMapper
//                              ,AuditService auditService
                                                    ) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            id = "usersEventConsumer",
            topics = "users",
            groupId = "users-audit-group"
    )
    public void consumeUsersEvent(String message) {
        try {
            //Парсим JSON в объект
            UsersEvent event = objectMapper.readValue(message, UsersEvent.class);

            // Логируем структурированно
            logger.info("=== Users Event Received ===");
            logger.info("Event ID: {}", event.getEventId());
            logger.info("Event Type: {}", event.getEventType());
            logger.info("User ID: {}", event.getUserID());
            logger.info("Operation: {}", event.getOperation());
            logger.info("name: {}", event.getName());
            logger.info("surename: {}", event.getSurename());
            logger.info("role: {}", event.getRole());
            logger.info("Timestamp: {}", event.getTimestamp());
            logger.info("===========================");

            // Здесь я могу делать бизнес-логику:
            // 1. Сохранять в таблицу аудита
//            auditService.saveAuditRecord(event);

            // 2. Обновлять поисковый индекс (Elasticsearch)
            // searchIndex.updateIndex(event);

            // 3. Отправлять уведомления
            // notificationService.notifyAboutBook(event);
        } catch (Exception e) {
            logger.error("Error processing book event: {}", e.getMessage());
            //TODO Здесь нужно отправлять в Dead Letter Queue
        }
    }
}