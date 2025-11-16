package org.example.ticketmanagementservice.mapper;

import org.example.ticketmanagementservice.api.model.TicketPurchaseRequest;
import org.example.ticketmanagementservice.dtos.TicketReadDTO;
import org.example.ticketmanagementservice.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    // Convert entity → read DTO
    @Mapping(target = "status", source = "status")
    TicketReadDTO toDto(Ticket entity);

    List<TicketReadDTO> toDtoList(List<Ticket> entities);

    // Convert purchase request (generated OpenAPI DTO) → entity (initial creation)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "routeId", source = "routeId"),
            @Mapping(target = "startStopId", source = "startStopId"),
            @Mapping(target = "endStopId", source = "endStopId"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "status", ignore = true),           // set in service
            @Mapping(target = "purchaseDate", ignore = true),     // set when saving
            @Mapping(target = "qrCode", ignore = true),            // generated later
            @Mapping(target = "payment", ignore = true)           // set separately
    })
    Ticket toEntity(TicketPurchaseRequest dto);
}