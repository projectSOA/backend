package org.example.ticketmanagementservice.mapper;

import org.example.ticketmanagementservice.api.model.TicketPurchaseRequest;
import org.example.ticketmanagementservice.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    // Convert purchase request (generated OpenAPI DTO) → entity (initial creation)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "busId", source = "busId"),
            @Mapping(target = "startStopId", source = "startStopId"),
            @Mapping(target = "endStopId", source = "endStopId"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "status", ignore = true),           // set in service
            @Mapping(target = "purchaseDate", ignore = true),     // set when saving
            @Mapping(target = "expiresAt", ignore = true),
            @Mapping(target = "validatedAt", ignore = true),
            @Mapping(target = "qrCode", ignore = true),            // generated later
            @Mapping(target = "qrCodeGeneratedAt", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "payment", ignore = true)
    })
    Ticket toEntity(TicketPurchaseRequest dto);
}