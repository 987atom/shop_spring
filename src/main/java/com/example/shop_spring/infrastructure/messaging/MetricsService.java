package com.example.shop_spring.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Простой сервис для сбора метрик по событиям.
 * В будущем можно заменить на Micrometer/Prometheus.
 */
@Service
@Slf4j
public class MetricsService {
    private final AtomicLong eventsPublished = new AtomicLong(0);
    private final AtomicLong eventsProcessed = new AtomicLong(0);
    private final AtomicLong eventsFailed = new AtomicLong(0);

    public void incrementPublished() {
        long count = eventsPublished.incrementAndGet();
        if (count % 100 == 0) {
            log.info("📊 Всего опубликовано событий: {}", count);
        }
    }

    public void incrementProcessed() {
        long count = eventsProcessed.incrementAndGet();
        if (count % 100 == 0) {
            log.info("📊 Всего обработано событий: {}", count);
        }
    }

    public void incrementFailed() {
        long count = eventsFailed.incrementAndGet();
        log.warn("📊 Всего ошибок обработки событий: {}", count);
    }

    public String getMetrics() {
        return String.format(
                "Metrics: published=%d, processed=%d, failed=%d",
                eventsPublished.get(), eventsProcessed.get(), eventsFailed.get()
        );
    }
}

