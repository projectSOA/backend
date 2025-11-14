package com.example.subscription_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for validating a user's subscription.
 *
 * Fields:
 * - granted: true if access is allowed, false otherwise
 * - reason: reason for denial if any
 * - remainingTickets: number of tickets left for the subscription
 * - expiryDate: exact date and time when the subscription expires
 * - message: human-readable message for frontend or logs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateUserSubscriptionResponse {

    private Boolean granted;
    private String reason;
    private Integer remainingTickets;
    private LocalDate expiryDate;
    private String message;

    public static ValidateUserSubscriptionResponse granted(Integer remainingTickets, LocalDate expiryDate) {
        return new ValidateUserSubscriptionResponse(
                true,
                "VALID",
                remainingTickets,
                expiryDate,
                "Access granted. Welcome aboard!"
        );
    }

    public static ValidateUserSubscriptionResponse denied(String reason, String message) {
        return new ValidateUserSubscriptionResponse(
                false,
                reason,
                null,
                null,
                message
        );
    }
}