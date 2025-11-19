package org.example.geolocationservice.dtos;

import java.util.UUID;

public record WebSocketMessage(
        String type,
        UUID trajectoryId
) {}