package org.example.geolocationservice.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.geolocationservice.Services.BusPositionSimulationService;
import org.example.geolocationservice.dtos.BusPositionUpdate;
import org.example.geolocationservice.dtos.WebSocketMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.Disposable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BusPositionWebSocketHandler extends TextWebSocketHandler {

    private final BusPositionSimulationService simulationService;
    private final ObjectMapper objectMapper;
    private final Map<String, Disposable> subscriptions = new ConcurrentHashMap<>();

    public BusPositionWebSocketHandler(BusPositionSimulationService simulationService) {
        this.simulationService = simulationService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        WebSocketMessage wsMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);

        if ("subscribe".equals(wsMessage.type())) {
            subscribeToTrajectory(session, wsMessage.trajectoryId());
        } else if ("unsubscribe".equals(wsMessage.type())) {
            unsubscribeFromTrajectory(session);
        }
    }

    private void subscribeToTrajectory(WebSocketSession session, Long trajectoryId) {
        unsubscribeFromTrajectory(session);

        Disposable subscription = simulationService.startSimulation(trajectoryId)
                .subscribe(
                        positionUpdate -> sendPositionUpdate(session, positionUpdate),
                        error -> handleError(session, error),
                        () -> System.out.println("Simulation completed for trajectory: " + trajectoryId)
                );

        subscriptions.put(session.getId(), subscription);
        System.out.println("Client subscribed to trajectory: " + trajectoryId);
    }

    private void unsubscribeFromTrajectory(WebSocketSession session) {
        Disposable subscription = subscriptions.remove(session.getId());
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
            System.out.println("Client unsubscribed from trajectory");
        }
    }

    private void sendPositionUpdate(WebSocketSession session, BusPositionUpdate update) {
        try {
            if (session.isOpen()) {
                String json = objectMapper.writeValueAsString(update);
                session.sendMessage(new TextMessage(json));
            }
        } catch (Exception e) {
            System.err.println("Error sending position update: " + e.getMessage());
        }
    }

    private void handleError(WebSocketSession session, Throwable error) {
        System.err.println("Error in simulation: " + error.getMessage());
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage("{\"error\": \"" + error.getMessage() + "\"}"));
            }
        } catch (Exception e) {
            System.err.println("Error sending error message: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        unsubscribeFromTrajectory(session);
        System.out.println("WebSocket connection closed: " + status);
    }
}