package org.example.geolocationservice.dtos;

public record BusDTO(
        Long id,
        String busNumber,
        String licensePlate,
        Long trajectoryId

) {
}
