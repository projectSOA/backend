package org.example.geolocationservice.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record BusDTO(
        UUID id,
        String registrationNumber,
        LocalTime startTime,
        LocalTime endTime,
        String busStatus,
        LocalDate lastMaintenance,
        UUID routeId

) {
}
