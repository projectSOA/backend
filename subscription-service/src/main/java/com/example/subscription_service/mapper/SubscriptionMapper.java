package com.example.subscription_service.mapper;

import com.example.subscription_service.dto.request.CreateSubscriptionRequest;
import com.example.subscription_service.dto.request.UpdateSubscriptionRequest;
import com.example.subscription_service.dto.response.SubscriptionResponse;
import com.example.subscription_service.entity.Subscription;

public class SubscriptionMapper {

    public static Subscription toEntity(CreateSubscriptionRequest dto) {
        Subscription subscription = new Subscription();
        subscription.setPlan(dto.getPlan());
        subscription.setStatus(dto.getStatus());
        subscription.setPrice(dto.getPrice());
        subscription.setTotalTickets(dto.getTotalTickets());
        subscription.setValidityDays(dto.getValidityDays());
        subscription.setAutoRenewal(dto.getAutoRenewal() != null ? dto.getAutoRenewal() : false);
        subscription.setDescription(dto.getDescription());
        return subscription;
    }

    public static SubscriptionResponse toResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getPlan(),
                subscription.getStatus(),
                subscription.getPrice(),
                subscription.getTotalTickets(),
                subscription.getValidityDays(),
                subscription.getAutoRenewal(),
                subscription.getDescription()
        );
    }
}
