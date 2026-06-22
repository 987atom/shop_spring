package com.example.shop_spring.infrastructure.messaging.publisher;

import com.example.shop_spring.domain.events.base.DomainEvent;
import com.example.shop_spring.domain.events.user.UserCreatedEvent;
import com.example.shop_spring.domain.events.user.UserDeletedEvent;
import com.example.shop_spring.domain.events.user.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Публикует события, связанные с пользователями, в Kafka.
 *
 * Отвечает за:
 * 1. Сериализацию событий в JSON
 * 2. Отправку в правильный топик
 * 3. Обработку ошибок отправки
 * 4. Логирование успешных/неудачных отправок
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Название топика для событий пользователей.
     * Вынесено в конфигурацию, чтобы можно было менять без перекомпиляции
     */
    @Value("${app.kafka.topics.user-events:user-events}")
    private String userEventsTopic;

    /**
     * Публикует событие создания пользователя
     */
    public void publishUserCreated(UserCreatedEvent event) {
        publishEvent(event);
    }

    /**
     * Публикует событие обновления пользователя
     */
    public void publishUserUpdated(UserUpdatedEvent event) {
        publishEvent(event);
    }

    /**
     * Публикует событие удаления пользователя
     */
    public void publishUserDeleted(UserDeletedEvent event) {
        publishEvent(event);
    }

    /**
     * Универсальный метод для отправки любого доменного события.
     *
     * Принципы:
     * 1. Ключ = userId (все события по одному пользователю попадают в одну партицию)
     * 2. Асинхронная отправка с callback для логирования
     * 3. Ошибки не выбрасываются (чтобы не сломать основной бизнес-процесс)
     */
    private void publishEvent(DomainEvent event) {
        try {
            // Определяем ID пользователя для ключа
            String key = extractUserId(event);

            // Асинхронная отправка в Kafka
            CompletableFuture<SendResult<String, Object>> future =
                    kafkaTemplate.send(userEventsTopic, key, event);

            // Добавляем callback для логирования результата
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("✅ Событие опубликовано: eventId={}, topic={}, partition={}, offset={}",
                            event.getEventId(),
                            userEventsTopic,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset()
                    );
                } else {
                    // Логируем ошибку, но не выбрасываем исключение
                    // Потому что основная операция (сохранение в БД) уже выполнена
                    log.error("❌ Не удалось опубликовать событие: eventId={}, error={}",
                            event.getEventId(), ex.getMessage(), ex);

                    // TODO: В будущем добавить механизм повторной отправки
                    // - Сохранять в БД (Outbox pattern)
                    // - Отправлять в Dead Letter Topic
                }
            });

        } catch (Exception e) {
            // Логируем любые ошибки, связанные с отправкой
            log.error("❌ Критическая ошибка при отправке события в Kafka: {}", e.getMessage(), e);
        }
    }

    /**
     * Извлекает ID пользователя из события для использования как ключ Kafka.
     *
     * Зачем нужен ключ?
     * - Все события по одному пользователю попадают в одну партицию
     * - Гарантирует порядок событий для конкретного пользователя
     * - Позволяет масштабировать обработку по пользователям
     */
    private String extractUserId(DomainEvent event) {
        if (event instanceof UserCreatedEvent) {
            return String.valueOf(((UserCreatedEvent) event).getUserId());
        } else if (event instanceof UserUpdatedEvent) {
            return String.valueOf(((UserUpdatedEvent) event).getUserId());
        } else if (event instanceof UserDeletedEvent) {
            return String.valueOf(((UserDeletedEvent) event).getUserId());
        } else {
            // Если вдруг прилетит непонятное событие
            log.warn("Неизвестный тип события для извлечения userId: {}", event.getClass());
            return "unknown";
        }
    }


}
