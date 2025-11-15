package org.example.ticketmanagementservice.dtos;

import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.example.ticketmanagementservice.entities.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentDTO(
        UUID id,                // Payment identifier
        UUID userId,            // User making the payment
        UUID relatedId,         // Can be ticketId or subscriptionId
        BigDecimal price,      // Paid amount
        PaymentMethod method,          // Enum name: CREDIT_CARD, PAYPAL, MOBILE_MONEY, etc.
        PaymentStatus status,          // Enum name: PENDING, COMPLETED, FAILED, REFUNDED
        String transactionId,   // External gateway reference
        LocalDateTime paymentDate
) {}