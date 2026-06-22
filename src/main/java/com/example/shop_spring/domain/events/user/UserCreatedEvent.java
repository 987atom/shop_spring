package com.example.shop_spring.domain.events.user;

import com.example.shop_spring.domain.events.base.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Событие, которое возникает, когда создается новый пользователь.
 *
 * Содержит ВСЮ информацию о созданном пользователе,
 * чтобы потребителям не пришлось делать дополнительный запрос в БД.
 */

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent extends DomainEvent{
    private static final String EVENT_VERSION = "1.0";
    private final Long userId;
    private final String name;
    private final String email;
    private final UserEventType eventType;

    /**
     * Конструктор события
     */
    public UserCreatedEvent(Long userId, String name, String email) {
        super(EVENT_VERSION);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.eventType = UserEventType.CREATED;
    }

}
