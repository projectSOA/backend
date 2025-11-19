package org.example.geolocationservice.dtos;

import java.util.UUID;

public record StationDTO(
        UUID id,
        String name,
        Double latitude,
        Double longitude
) {
}
