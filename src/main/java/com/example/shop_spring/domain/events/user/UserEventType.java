package com.example.shop_spring.domain.events.user;

/**
 * Типы событий, связанных с пользователями.
 * Используется для легкой фильтрации и маршрутизации.
 */
public enum UserEventType {
    CREATED,
    UPDATED,
    DELETED
}