package org.example.geolocationservice.dtos;

public record TrajectoryDTO(
        Long id,
        String name,
        StationDTO startStation,
        StationDTO endStation
) {
}
