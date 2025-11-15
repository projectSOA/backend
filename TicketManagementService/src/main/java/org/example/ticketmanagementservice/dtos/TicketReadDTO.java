package org.example.ticketmanagementservice.dtos;

import org.example.ticketmanagementservice.entities.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketReadDTO(
        UUID id,
        UUID userId,
        UUID routeId,
        UUID startStopId,
        UUID endStopId,
        BigDecimal price,
        TicketStatus status,             // TicketStatus
        LocalDateTime purchaseDate,
        String qrCode
) {}