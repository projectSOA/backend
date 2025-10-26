package org.example.ticketmanagementservice.mapper;

import org.example.ticketmanagementservice.dtos.SubscriptionPurchaseRequestDTO;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SubscriptionPurchaseToPaymentMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            // map fields
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "relatedId", source = "subscriptionId"), // link payment to the subscription
            @Mapping(target = "price", source = "price"),


            // runtime/populated later (by app logic)
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "transactionId", ignore = true),
            @Mapping(target = "paymentDate", ignore = true)

            // if you add one later:
            // @Mapping(target = "idempotencyKey", ignore = true)
    })
    Payment toPayment(SubscriptionPurchaseRequestDTO req);
}