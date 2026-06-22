package com.example.shop_spring.domain.events.user;

import com.example.shop_spring.domain.events.base.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Событие, которое возникает при обновлении пользователя.
 *
 * Содержит ТОЛЬКО ИЗМЕНЕННЫЕ поля и ID пользователя.
 * Потребитель может получить полные данные через REST API, если нужно.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserUpdatedEvent extends DomainEvent{
    private static final String EVENT_VERSION = "1.0";

    private final Long userId;
    private final String name;
    private final String email;
    private final UserEventType eventType;

    public UserUpdatedEvent(Long userId, String name, String email) {
        super(EVENT_VERSION);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.eventType = UserEventType.UPDATED;
    }
}
