package com.example.shop_spring;

import com.example.shop_spring.kafka.Services.Producer.ProducerService;
import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Enabled
public class ShopSpringApplication {

    private final ProducerService producer;

//    public static void main(String[] args) {
//        SpringApplication.run(ShopSpringApplication.class, args);
//    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ShopSpringApplication.class);
//        application.setWebApplicationType(WebApplicationType.NONE); это что бы в консоли без web части запускался
        SpringApplication.run(ShopSpringApplication.class, args);
        application.run(args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            for (String arg : args) {
                switch (arg) {
                    case "--producer":
                        this.producer.sendMessage("awalther", "t-shirts");
                        this.producer.sendMessage("htanaka", "t-shirts");
                        this.producer.sendMessage("htanaka", "batteries");
                        this.producer.sendMessage("eabara", "t-shirts");
                        this.producer.sendMessage("htanaka", "t-shirts");
                        this.producer.sendMessage("jsmith", "book");
                        this.producer.sendMessage("awalther", "t-shirts");
                        this.producer.sendMessage("jsmith", "batteries");
                        this.producer.sendMessage("jsmith", "gift card");
                        this.producer.sendMessage("eabara", "t-shirts");
                        break;
                    case "--consumer":
                        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("myConsumer");
                        listenerContainer.start();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Autowired
    ShopSpringApplication(ProducerService producer) {
        this.producer = producer;
    }

    @Autowired
    @Lazy
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
}
