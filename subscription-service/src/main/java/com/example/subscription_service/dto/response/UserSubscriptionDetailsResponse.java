package com.example.subscription_service.dto.response;

import com.example.subscription_service.enums.UserSubscriptionStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Detailed view with additional info
public class UserSubscriptionDetailsResponse {
    private UUID id;
    private UUID userId;
    private UserSubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfTicketsLeft;
    private Integer totalTickets; // Original total
    private String qrCode;
    private LocalDateTime lastUsed;
    private SubscriptionResponse subscription;
    private Boolean isActive; // Computed field
    private Boolean isExpired; // Computed field
    private Integer daysRemaining; // Computed field
}
