package com.example.subscription_service.dto.response;

import com.example.subscription_service.enums.SubscriptionPlan;
import com.example.subscription_service.enums.SubscriptionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResponse {
    private UUID id;
    private String title;
    private SubscriptionPlan plan;
    private SubscriptionStatus status;
    private BigDecimal price;
    private Integer totalTickets;
    private Integer validityDays;
    private Boolean autoRenewal;
    private String description;

}
