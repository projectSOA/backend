package com.example.subscription_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ValidateUserSubscriptionRequest {

    @NotNull(message = "User subscription ID is required")
    private UUID userSubscriptionId; // From QR code / JWT

    private UUID userId;
}
