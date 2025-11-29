package com.example.subscription_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for validating a user's subscription.
 * userSubscriptionId: required, from QR code or JWT
 * userId: optional, inferred from JWT if not provided
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateUserSubscriptionRequest {

    @NotNull(message = "QR code is required")
    private String qrCode;
}
