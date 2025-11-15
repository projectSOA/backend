package org.example.ticketmanagementservice.mapper;


import org.example.ticketmanagementservice.dtos.TicketPurchaseRequestDTO;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketPurchaseToPaymentMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "relatedId", ignore = true), // you’ll set ticketId later when you have it
            @Mapping(target = "price", source = "price"),

            @Mapping(target = "status", ignore = true),
            @Mapping(target = "transactionId", ignore = true),
            @Mapping(target = "paymentDate", ignore = true),
            @Mapping(target = "idempotencyKey", source = "idempotencyKey")
    })
    Payment toPayment(TicketPurchaseRequestDTO req);
}