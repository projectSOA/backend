package org.example.ticketmanagementservice.mapper;

import org.example.ticketmanagementservice.api.model.SubscriptionPurchaseRequest;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SubscriptionPurchaseToPaymentMapper {

    /**
     * Map SubscriptionPurchaseRequest (generated OpenAPI DTO) to Payment entity
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "relatedId", source = "subscriptionId"), // link payment to the subscription
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "method", source = "method", qualifiedByName = "stringToPaymentMethod"),
            @Mapping(target = "idempotencyKey", source = "idempotencyKey"),
            // runtime/populated later (by app logic)
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "transactionId", ignore = true),
            @Mapping(target = "paymentDate", ignore = true),
            @Mapping(target = "paymentPurpose", ignore = true)
    })
    Payment toPayment(SubscriptionPurchaseRequest req);

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