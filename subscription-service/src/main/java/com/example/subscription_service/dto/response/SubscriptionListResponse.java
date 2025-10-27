package com.example.subscription_service.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionListResponse {
    private List<SubscriptionResponse> subscriptions;
    private Integer totalPlans;
}
