package com.example.subscription_service.controller;

import com.example.subscription_service.dto.request.CreateSubscriptionRequest;
import com.example.subscription_service.dto.request.UpdateSubscriptionRequest;
import com.example.subscription_service.dto.response.SubscriptionResponse;
import com.example.subscription_service.enums.SubscriptionStatus;
import com.example.subscription_service.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscriptions")
@CrossOrigin(origins = "http://localhost:5173")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> createSubscription(@RequestBody CreateSubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.createSubscription(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> updateSubscription(@PathVariable UUID id,
                                                                  @RequestBody UpdateSubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, request));
    }

    @GetMapping("{id}")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable UUID id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SubscriptionResponse>> getSubscriptionsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsPage(page, size));
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<SubscriptionResponse> changeStatus(@PathVariable UUID id,
                                                             @RequestParam SubscriptionStatus status) {
        return ResponseEntity.ok(subscriptionService.toggleStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> deleteSubscription(@PathVariable UUID id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }


}
