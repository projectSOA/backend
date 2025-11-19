package org.example.geolocationservice.dtos;

import java.util.UUID;

public record BusPositionUpdate(
        UUID trajectoryId,
        UUID busId,
        String busNumber,
        Position position,
        Double speed,
        String timestamp,
        String status
) {
    public record Position(
            Double latitude,
            Double longitude
    ) {}
}