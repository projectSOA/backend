package com.example.subscription_service.dto.request;


import com.example.subscription_service.enums.SubscriptionPlan;
import com.example.subscription_service.enums.SubscriptionStatus;
import jakarta.validation.constraints.Positive;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
public class CreateSubscriptionRequest {

    @NotNull(message = "Plan type is required")
    private SubscriptionPlan plan;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Total tickets is required")
    @Positive(message = "Total tickets must be positive")
    private Integer totalTickets;

    @NotNull(message = "Validity days is required")
    @Positive(message = "Validity days must be positive")
    private Integer validityDays;

    private Boolean autoRenewal;

    private String description;

    @NotNull(message = "Status is required")
    private SubscriptionStatus status;
}
