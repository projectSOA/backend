package org.example.notificationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;



@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue delayNotificationQueue() {
        return new Queue("delayNotificationQueue");
    }
}
