package org.example.notificationservice.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class MessageBrokerProperties {

    private String exchangeName;
    private QueueNames queue = new QueueNames();
    private RoutingKeys routingKey = new RoutingKeys();

    @Data
    public static class QueueNames {
        private String delay;
        private String cancel;


    }

    @Data
    public static class RoutingKeys {
        private String delay;
        private String cancel;
    }
}
