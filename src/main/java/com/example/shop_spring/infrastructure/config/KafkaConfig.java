package com.example.shop_spring.infrastructure.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация Kafka для приложения.
 *
 * Здесь мы:
 * 1. Создаем топики, которые будут использоваться
 * 2. Настраиваем параметры топиков (партиции, реплики)
 * 3. Подключаем админ-клиент для управления топиками
 */
@Configuration
public class KafkaConfig {

    /**
     * Адрес Kafka брокера из конфигурации
     */
    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    /**
     * Создает админ-клиент для управления топиками.
     * Spring автоматически создаст топики при старте приложения.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    /**
     * Топик для событий пользователей.
     *
     * Параметры:
     * - partitions: 3 (для параллельной обработки)
     * - replicationFactor: 1 (для разработки, в проде ставим 3)
     *
     * Почему 3 партиции?
     * - Можно запустить 3 экземпляра Consumer для параллельной обработки
     * - Каждый экземпляр читает свою партицию
     * - Увеличиваем пропускную способность системы
     */
    @Bean
    public NewTopic userEventsTopic() {
        return new NewTopic("user-events", 3, (short) 1);
    }

    /**
     * Топик для dead-letter сообщений (ошибки).
     * Сюда попадают события, которые не удалось обработать.
     * Позволяет не терять данные и анализировать проблемы.
     */
    @Bean
    public NewTopic userEventsDeadLetterTopic() {
        return new NewTopic("user-events-dlq", 1, (short) 1);
    }
}
