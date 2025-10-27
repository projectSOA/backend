package com.example.subscription_service.dto.request;

import com.example.subscription_service.enums.UserSubscriptionStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserSubscriptionRequest {

    private UserSubscriptionStatus status;
    private LocalDate endDate; // For renewal/extension
    private Integer numberOfTicketsLeft; // Admin adjustment
}
