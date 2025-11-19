package org.example.geolocationservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final BusPositionWebSocketHandler busPositionHandler;

    public WebSocketConfig(BusPositionWebSocketHandler busPositionHandler) {
        this.busPositionHandler = busPositionHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(busPositionHandler, "/ws/bus-positions")
                .setAllowedOrigins("*");
    }
}
