package org.example.geolocationservice.dtos;

public record StationDTO(
        Long id,
        String name,
        Double latitude,
        Double longitude
) {
}
