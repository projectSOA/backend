package org.example.ticketmanagementservice.mapper;

import org.example.ticketmanagementservice.api.model.TicketResponse;
import org.example.ticketmanagementservice.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketApiMapper {

    @Mappings({
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "purchaseDate", expression = "java(toOffsetDateTime(ticket.getPurchaseDate()))"),
            @Mapping(target = "expiresAt", expression = "java(toOffsetDateTime(ticket.getExpiresAt()))"),
            @Mapping(target = "validatedAt", expression = "java(toOffsetDateTime(ticket.getValidatedAt()))"),
            @Mapping(target = "paymentId", source = "payment.id")
    })
    TicketResponse toResponse(Ticket ticket);

    List<TicketResponse> toResponseList(List<Ticket> tickets);

    default OffsetDateTime toOffsetDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.atOffset(ZoneOffset.UTC) : null;
    }
}



