package com.example.shop_spring.domain.events.user;

import com.example.shop_spring.Entitys.CartEntity;
import com.example.shop_spring.domain.events.base.DomainEvent;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

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
    private final String surename;
    private final String role;
    private final UserEventType eventType;
    private final List<CartEntity> cart;

    public UserUpdatedEvent(Long userId, String name, String surename, String role, List<CartEntity> cart) {
        super(EVENT_VERSION);
        this.userId = userId;
        this.name = name;
        this.surename = surename;
        this.role = role;
        this.cart = cart;
        this.eventType = UserEventType.UPDATED;
    }
}
