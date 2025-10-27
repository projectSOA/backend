package com.example.subscription_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateUserSubscriptionResponse {
    private Boolean granted;
    private String reason;
    private Integer remainingTickets;
    private LocalDate expiryDate;
    private String message;

    // Convenience methods
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