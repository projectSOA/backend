package com.example.subscription_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO used when a user subscribes to a subscription plan.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserSubscriptionRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Subscription ID is required")
    private UUID subscriptionId;
}
