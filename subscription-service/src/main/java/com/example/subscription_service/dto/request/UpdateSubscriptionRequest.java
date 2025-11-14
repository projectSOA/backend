package com.example.subscription_service.dto.request;

import com.example.subscription_service.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubscriptionRequest {
    private BigDecimal price;
    private Integer totalTickets;
    private Integer validityDays;
    private SubscriptionStatus status;
    private Boolean autoRenewal;
    private String description;
}
