package org.example.ticketmanagementservice.mapper;

import org.example.ticketmanagementservice.api.model.TicketPurchaseRequest;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketPurchaseToPaymentMapper {

    /**
     * Map TicketPurchaseRequest (generated OpenAPI DTO) to Payment entity
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "relatedId", ignore = true), // you'll set ticketId later when you have it
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "method", source = "paymentMethod", qualifiedByName = "stringToPaymentMethod"),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "transactionId", ignore = true),
            @Mapping(target = "paymentDate", ignore = true),
            @Mapping(target = "paymentPurpose", ignore = true),
            @Mapping(target = "idempotencyKey", source = "idempotencyKey")
    })
    Payment toPayment(TicketPurchaseRequest req);

    @Named("stringToPaymentMethod")
    default PaymentMethod stringToPaymentMethod(String method) {
        if (method == null) return null;
        try {
            return PaymentMethod.valueOf(method);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}