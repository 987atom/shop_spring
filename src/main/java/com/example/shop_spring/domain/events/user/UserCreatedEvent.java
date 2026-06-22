package com.example.shop_spring.domain.events.user;

import com.example.shop_spring.Entitys.CartEntity;
import com.example.shop_spring.domain.events.base.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

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
    private final String surename;
    private final String role;
    private final List<CartEntity> cart;
    private final UserEventType eventType;

    /**
     * Конструктор события
     */
    public UserCreatedEvent(Long userId, String name, String surename, String role, List<CartEntity> cart) {
        super(EVENT_VERSION);
        this.userId = userId;
        this.name = name;
        this.surename = surename;
        this.role = role;
        this.cart = cart;
        this.eventType = UserEventType.CREATED;
    }

}
