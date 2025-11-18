package org.example.gatewayservice;

import org.springframework.stereotype.Component;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final WebClient webClient;

    public JwtAuthFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081")
                .build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            token = token.substring(7);

            Map<String, String> body = new HashMap<>();
            body.put("token", token);
            if (config.role != null) {
                body.put("role", config.role);
            }

            return webClient.post()
                    .uri("/api/v1/auth/validate-role")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isAuthorized -> {
                        if (Boolean.TRUE.equals(isAuthorized)) {
                            exchange.getRequest()
                                    .mutate()
                                    .header("X-USER-TOKEN", token)
                                    .build();
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            return exchange.getResponse().setComplete();
                        }
                    });
        };
    }

    public static class Config {
        private String role;
        public Config() {}
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
