package org.example.geolocationservice.dtos;

public record BusPositionUpdate(
        Long trajectoryId,
        Long busId,
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