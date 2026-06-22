package com.example.shop_spring.infrastructure.messaging.listener;

import com.example.shop_spring.domain.events.user.UserCreatedEvent;
import com.example.shop_spring.domain.events.user.UserDeletedEvent;
import com.example.shop_spring.domain.events.user.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Слушает события пользователей из Kafka.
 *
 * Здесь обрабатываются все события, которые приходят из других сервисов
 * или из этого же сервиса (для логирования/аудита).
 *
 * Важно: Consumer должен быть идемпотентным!
 * Одно событие может быть доставлено несколько раз.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserEventListener {

    /**
     * Слушает события создания пользователей.
     *
     * Использование:
     * 1. @KafkaListener - указывает, какой топик слушать
     * 2. @Payload - получает тело сообщения как Java объект
     * 3. Acknowledgment - ручное подтверждение (контролируем, когда считать сообщение обработанным)
     *
     * Ручное подтверждение (manual ack) нужно, чтобы:
     * - Подтвердить только после полной обработки
     * - Если обработка упала - сообщение не подтверждается и будет отправлено снова
     */
    @KafkaListener(
            topics = "${app.kafka.topics.user-events:user-events}",
            groupId = "${app.kafka.consumer.group-id:shop-service-group}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserCreatedEvent(
            @Payload UserCreatedEvent event,
            Acknowledgment acknowledgment
    )
    {
        try {
            log.info("📥 Получено событие создания пользователя: userId={}, name={}, eventId={}",
                    event.getUserId(),
                    event.getName(),
                    event.getEventId()
            );

            // ===== ЗДЕСЬ ВАША БИЗНЕС-ЛОГИКА =====
            // Например:
            // 1. Отправить приветственное письмо
            // 2. Создать запись в audit логе
            // 3. Обновить кэш
            // 4. Вызвать внешний API

            // Пример: логируем в отдельную таблицу
            // auditService.logUserCreated(event.getUserId(), event.getEmail());

            // Если все успешно - подтверждаем
            acknowledgment.acknowledge();
            log.info("✅ Событие создания обработано и подтверждено: {}", event.getEventId());

        } catch (Exception e) {
            // Если произошла ошибка - НЕ подтверждаем
            // Сообщение будет доставлено снова (после timeout)
            log.error("❌ Ошибка обработки события создания пользователя: eventId={}, error={}",
                    event.getEventId(), e.getMessage(), e);

            // TODO: В будущем - отправить в Dead Letter Topic после N попыток
            throw new RuntimeException("Failed to process user created event", e);
        }
    }

    /**
     * Слушает события обновления пользователей
     */
    @KafkaListener(
            topics = "${app.kafka.topics.user-events:user-events}",
            groupId = "${app.kafka.consumer.group-id:shop-service-group}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserUpdatedEvent(
            @Payload UserUpdatedEvent event,
            Acknowledgment acknowledgment
    ) {
        try {
            log.info("📥 Получено событие обновления пользователя: userId={}, eventId={}",
                    event.getUserId(),
                    event.getEventId()
            );

            // Ваша бизнес-логика здесь
            // auditService.logUserUpdated(event.getUserId());

            acknowledgment.acknowledge();
            log.info("✅ Событие обновления обработано и подтверждено: {}", event.getEventId());

        } catch (Exception e) {
            log.error("❌ Ошибка обработки события обновления: eventId={}, error={}",
                    event.getEventId(), e.getMessage(), e);
            throw new RuntimeException("Failed to process user updated event", e);
        }
    }

    /**
     * Слушает события удаления пользователей
     */
    @KafkaListener(
            topics = "${app.kafka.topics.user-events:user-events}",
            groupId = "${app.kafka.consumer.group-id:shop-service-group}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserDeletedEvent(
            @Payload UserDeletedEvent event,
            Acknowledgment acknowledgment
    ) {
        try {
            log.info("📥 Получено событие удаления пользователя: userId={}, eventId={}",
                    event.getUserId(),
                    event.getEventId()
            );

            // Ваша бизнес-логика здесь
            // auditService.logUserDeleted(event.getUserId());

            acknowledgment.acknowledge();
            log.info("✅ Событие удаления обработано и подтверждено: {}", event.getEventId());

        } catch (Exception e) {
            log.error("❌ Ошибка обработки события удаления: eventId={}, error={}",
                    event.getEventId(), e.getMessage(), e);
            throw new RuntimeException("Failed to process user deleted event", e);
        }
    }
}

