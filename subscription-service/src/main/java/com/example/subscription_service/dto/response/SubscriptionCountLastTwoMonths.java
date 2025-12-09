package com.example.subscription_service.dto.response;

public record SubscriptionCountLastTwoMonths(
        long thisMonth,
        long lastMonth
) {}
