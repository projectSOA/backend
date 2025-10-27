package com.example.subscription_service.dto.request;

import com.example.subscription_service.enums.SubscriptionStatus;
import com.example.subscription_service.enums.UserSubscriptionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateSubscriptionRequest {
    private BigDecimal price;
    private Integer totalTickets;
    private Integer validityDays;
    private SubscriptionStatus status;
    private Boolean autoRenewal;
    private String description;
}
