package org.example.geolocationservice.dtos;

import java.util.UUID;

public record TrajectoryDTO(
        UUID id,
        String name,
        StationDTO startStation,
        StationDTO endStation
) {
}
