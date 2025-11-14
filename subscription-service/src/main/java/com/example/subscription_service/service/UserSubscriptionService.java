package com.example.subscription_service.service;

import com.example.subscription_service.dto.request.CreateUserSubscriptionRequest;
import com.example.subscription_service.dto.request.ValidateUserSubscriptionRequest;
import com.example.subscription_service.dto.response.UserSubscriptionResponse;
import com.example.subscription_service.dto.response.ValidateUserSubscriptionResponse;
import com.example.subscription_service.entity.Subscription;
import com.example.subscription_service.entity.UserSubscription;
import com.example.subscription_service.enums.SubscriptionStatus;
import com.example.subscription_service.enums.UserSubscriptionStatus;
import com.example.subscription_service.mapper.UserSubscriptionMapper;
import com.example.subscription_service.repository.SubscriptionRepository;
import com.example.subscription_service.repository.UserSubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;

    public UserSubscriptionService(UserSubscriptionRepository userSubscriptionRepository, SubscriptionRepository subscriptionRepository) {
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public UserSubscriptionResponse createUserSubscription(CreateUserSubscriptionRequest request) {
        Subscription sub = subscriptionRepository.findById(request.getSubscriptionId())
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        UserSubscription us = new UserSubscription();
        us.setUserId(request.getUserId());
        us.setSubscription(sub);
        us.setStatus(sub.getStatus() == SubscriptionStatus.ACTIVE ? UserSubscriptionStatus.ACTIVE : UserSubscriptionStatus.SUSPENDED);
        us.setStartDate(LocalDate.now());
        us.setEndDate(LocalDate.now().plusDays(sub.getValidityDays()));
        us.setNumberTicketsLeft(sub.getTotalTickets());
        us.setQrCode(generateQrCode()); // implement this method
        userSubscriptionRepository.save(us);

        return UserSubscriptionMapper.toResponse(us);
    }

    public ValidateUserSubscriptionResponse validateUserSubscription(ValidateUserSubscriptionRequest request) {
        UserSubscription us = userSubscriptionRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        if (us.getStatus() != UserSubscriptionStatus.ACTIVE) {
            return ValidateUserSubscriptionResponse.denied("NOT_ACTIVE", "Subscription is not active");
        }
        if (us.getNumberTicketsLeft() <= 0) {
            return ValidateUserSubscriptionResponse.denied("NO_TICKETS", "No remaining tickets");
        }
        if (us.getEndDate().isBefore(LocalDate.now())) {
            return ValidateUserSubscriptionResponse.denied("EXPIRED", "Subscription has expired");
        }

        return ValidateUserSubscriptionResponse.granted(us.getNumberTicketsLeft(), us.getEndDate());
    }

    @Transactional
    public UserSubscriptionResponse decrementTicket(UUID userSubscriptionId) {
        UserSubscription us = userSubscriptionRepository.findById(userSubscriptionId)
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        if (us.getNumberTicketsLeft() <= 0) throw new RuntimeException("No tickets left");
        us.setNumberTicketsLeft(us.getNumberTicketsLeft() - 1);
        return UserSubscriptionMapper.toResponse(userSubscriptionRepository.save(us));
    }

    public List<UserSubscriptionResponse> getUserSubscriptions(UUID userId) {
        return userSubscriptionRepository.findAllByUserId(userId).stream()
                .map(UserSubscriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserSubscriptionResponse renewSubscription(UUID userSubscriptionId) {
        UserSubscription us = userSubscriptionRepository.findById(userSubscriptionId)
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        // Only ACTIVE or SUSPENDED subscriptions can be renewed
        if (us.getStatus() != UserSubscriptionStatus.ACTIVE &&
                us.getStatus() != UserSubscriptionStatus.SUSPENDED) {
            throw new RuntimeException("Subscription cannot be renewed in its current state");
        }

        // Extend end date by the original subscription validity
        int validityDays = us.getSubscription().getValidityDays();
        us.setEndDate(us.getEndDate().plusDays(validityDays));

        // Optionally reset ticket count
        us.setNumberTicketsLeft(us.getSubscription().getTotalTickets());

        // If it was suspended, activate it
        if (us.getStatus() == UserSubscriptionStatus.SUSPENDED) {
            us.setStatus(UserSubscriptionStatus.ACTIVE);
        }

        return UserSubscriptionMapper.toResponse(userSubscriptionRepository.save(us));
    }

    @Transactional
    public UserSubscriptionResponse updateUserSubscription(UUID userSubscriptionId,
                                                           UserSubscriptionStatus status,
                                                           Integer numberTicketsLeft,
                                                           LocalDate endDate) {
        UserSubscription us = userSubscriptionRepository.findById(userSubscriptionId)
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        if (status != null) us.setStatus(status);
        if (numberTicketsLeft != null) us.setNumberTicketsLeft(numberTicketsLeft);
        if (endDate != null) us.setEndDate(endDate);

        return UserSubscriptionMapper.toResponse(userSubscriptionRepository.save(us));
    }

    @Transactional
    public void deleteUserSubscription(UUID userSubscriptionId) {
        if (!userSubscriptionRepository.existsById(userSubscriptionId)) {
            throw new RuntimeException("User subscription not found");
        }
        userSubscriptionRepository.deleteById(userSubscriptionId);
    }



    private String generateQrCode() {
        // placeholder for QR code generation logic
        return UUID.randomUUID().toString();
    }

}
