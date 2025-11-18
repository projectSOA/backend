package com.example.subscription_service.mapper;

import com.example.subscription_service.dto.response.UserSubscriptionResponse;
import com.example.subscription_service.entity.UserSubscription;

public class UserSubscriptionMapper {

    public static UserSubscriptionResponse toResponse(UserSubscription us) {
        return UserSubscriptionResponse.builder()
                .id(us.getId())
                .userId(us.getUserId())
                .status(us.getStatus())
                .startDate(us.getStartDate())
                .endDate(us.getEndDate())
                .numberOfTicketsLeft(us.getNumberTicketsLeft())
                .qrCode(us.getQrCode())
                .lastUsed(us.getLastUsed())
                .subscription(SubscriptionMapper.toResponse(us.getSubscription()))
                .build();
    }


}
