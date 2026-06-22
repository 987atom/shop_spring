package com.example.shop_spring.domain.events.user;

import com.example.shop_spring.domain.events.base.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Событие, которое возникает при удалении пользователя.
 *
 * Содержит только ID удаленного пользователя.
 * Это минимально необходимая информация для других сервисов.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDeletedEvent extends DomainEvent{
    private static final String EVENT_VERSION = "1.0";
    private final Long userId;
    private final UserEventType eventType;

    public UserDeletedEvent(Long userId) {
        super(EVENT_VERSION);
        this.userId = userId;
        this.eventType = UserEventType.DELETED;
    }
}
