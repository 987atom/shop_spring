package com.example.shop_spring.domain.events.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Базовый класс для всех доменных событий.
 * Все события в системе должны наследоваться от этого класса.
 *
 * Содержит общие для всех событий поля:
 * - eventId: уникальный идентификатор события (для отслеживания дубликатов)
 * - timestamp: время возникновения события
 * - eventVersion: версия формата события (для миграций)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainEvent {

    /**
     * Уникальный идентификатор события.
     * Используется для идемпотентности (чтобы не обработать одно событие дважды)
     */
    private String eventId;

    /**
     * Время возникновения события в UTC
     */
    private LocalDateTime timestamp;

    /**
     * Версия формата события.
     * Если мы меняем структуру события, увеличиваем версию,
     * чтобы потребители знали, как его парсить.
     */
    private String eventVersion;

    /**
     * Конструктор для создания события с автоматической генерацией ID
     */

    protected DomainEvent(String eventVersion) {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.eventVersion = eventVersion;
    }
}
