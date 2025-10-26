package org.example.ticketmanagementservice.dtos;

import org.example.ticketmanagementservice.entities.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record SubscriptionPurchaseRequestDTO(//Used for subscription service to make what the subService sends and the payment service idempotent
                                             UUID userId,
                                             UUID subscriptionId,// subscriptionId in this case (or ticketId in other flows)
                                             BigDecimal price,
                                             PaymentMethod method,         // CARD / PAYPAL / MOBILE_MONEY ...
                                             String idempotencyKey//An idempotency key is a unique identifier (string/UUID) sent by a client or caller when performing a request that should only be executed once,
) {}

//idemptency key used in context where we want the transaction executed once