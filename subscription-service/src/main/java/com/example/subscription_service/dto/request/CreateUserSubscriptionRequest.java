package com.example.subscription_service.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserSubscriptionRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Subscription ID is required")
    private UUID subscriptionId; // Which plan they're subscribing to
}
