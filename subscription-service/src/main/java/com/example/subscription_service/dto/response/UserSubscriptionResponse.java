package com.example.subscription_service.dto.response;

import com.example.subscription_service.enums.UserSubscriptionStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for a user's subscription.
 * Includes subscription plan details and QR code info.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSubscriptionResponse {
    private UUID id;
    private UUID userId;
    private UserSubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfTicketsLeft;
    private String qrCode; // Base64 encoded QR code
    private LocalDateTime lastUsed;
    private SubscriptionResponse subscription; // Nested subscription plan details
}
