package com.example.shop_spring.kafka.services.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

//    @KafkaListener(id = "myConsumer", topics = "users", groupId = "spring-boot", autoStartup = "false")
    @KafkaListener(id = "myConsumer", topics = "users", groupId = "spring-boot", autoStartup = "true")
    public void listen(String value,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        logger.info(String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
    }
}
