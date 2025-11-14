package com.example.subscription_service.service;

import com.example.subscription_service.dto.request.CreateSubscriptionRequest;
import com.example.subscription_service.dto.request.UpdateSubscriptionRequest;
import com.example.subscription_service.dto.response.SubscriptionResponse;
import com.example.subscription_service.entity.Subscription;
import com.example.subscription_service.enums.SubscriptionStatus;
import com.example.subscription_service.mapper.SubscriptionMapper;
import com.example.subscription_service.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {


    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public SubscriptionResponse createSubscription(CreateSubscriptionRequest request) {
        Subscription subscription = SubscriptionMapper.toEntity(request);
        Subscription saved = subscriptionRepository.save(subscription);
        return SubscriptionMapper.toResponse(saved);
    }

    public SubscriptionResponse getSubscriptionById(UUID id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription with id " + id + " not found"));
        return SubscriptionMapper.toResponse(subscription);
    }

    @Transactional
    public SubscriptionResponse updateSubscription(UUID id, UpdateSubscriptionRequest request) {
        Optional<Subscription> opt = subscriptionRepository.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Subscription not found");
        Subscription sub = opt.get();

        if (request.getPrice() != null) sub.setPrice(request.getPrice());
        if (request.getTotalTickets() != null) sub.setTotalTickets(request.getTotalTickets());
        if (request.getValidityDays() != null) sub.setValidityDays(request.getValidityDays());
        if (request.getStatus() != null) sub.setStatus(request.getStatus());
        if (request.getAutoRenewal() != null) sub.setAutoRenewal(request.getAutoRenewal());
        if (request.getDescription() != null) sub.setDescription(request.getDescription());

        return SubscriptionMapper.toResponse(subscriptionRepository.save(sub));
    }

    public List<SubscriptionResponse> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(SubscriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteSubscription(UUID id) {
        subscriptionRepository.deleteById(id);
    }

    @Transactional
    public SubscriptionResponse toggleStatus(UUID id, SubscriptionStatus status) {
        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        sub.setStatus(status);
        return SubscriptionMapper.toResponse(subscriptionRepository.save(sub));
    }
}
