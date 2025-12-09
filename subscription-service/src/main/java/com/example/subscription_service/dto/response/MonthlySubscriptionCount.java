package com.example.subscription_service.dto.response;


public record MonthlySubscriptionCount(
         int month,
       long count
) {}