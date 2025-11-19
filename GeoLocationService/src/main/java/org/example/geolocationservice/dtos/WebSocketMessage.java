package org.example.geolocationservice.dtos;

public record WebSocketMessage(
        String type, // "subscribe", "unsubscribe"
        Long trajectoryId
) {}