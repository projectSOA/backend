package org.example.ticketmanagementservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean("subscriptionServiceClient")
    public RestClient subscriptionServiceClient(
            RestClient.Builder builder,
            @Value("${subscription.service.url:http://localhost:8084}") String serverUrl) {
        return builder
                .baseUrl(serverUrl + "/api/v1")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}