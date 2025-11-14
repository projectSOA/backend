package com.example.subscription_service.controller;

import com.example.subscription_service.dto.request.CreateUserSubscriptionRequest;
import com.example.subscription_service.dto.request.ValidateUserSubscriptionRequest;
import com.example.subscription_service.dto.response.UserSubscriptionResponse;
import com.example.subscription_service.dto.response.ValidateUserSubscriptionResponse;
import com.example.subscription_service.enums.UserSubscriptionStatus;
import com.example.subscription_service.service.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-subscription")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @Autowired
    public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @PostMapping
    ResponseEntity<UserSubscriptionResponse> createUserSubscription(@RequestBody CreateUserSubscriptionRequest request) {
        return ResponseEntity.ok(userSubscriptionService.createUserSubscription(request));
    }

    // Validate a subscription (QR code)
    @PostMapping("/validate")
    public ResponseEntity<ValidateUserSubscriptionResponse> validateSubscription(@RequestBody ValidateUserSubscriptionRequest request) {
        return ResponseEntity.ok(userSubscriptionService.validateUserSubscription(request));
    }

    // Decrement ticket count
    @PostMapping("/{id}/decrement")
    public ResponseEntity<UserSubscriptionResponse> decrementTicket(@PathVariable UUID id) {
        return ResponseEntity.ok(userSubscriptionService.decrementTicket(id));
    }

    // Renew subscription
    @PostMapping("/{id}/renew")
    public ResponseEntity<UserSubscriptionResponse> renewSubscription(@PathVariable UUID id) {
        return ResponseEntity.ok(userSubscriptionService.renewSubscription(id));
    }

    // Update user subscription
    @PatchMapping("/{id}")
    public ResponseEntity<UserSubscriptionResponse> updateSubscription(@PathVariable UUID id,
                                                                       @RequestParam(required = false) UserSubscriptionStatus status,
                                                                       @RequestParam(required = false) Integer numberTicketsLeft,
                                                                       @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(userSubscriptionService.updateUserSubscription(id, status, numberTicketsLeft, endDate));
    }

    // Delete a user subscription
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable UUID id) {
        userSubscriptionService.deleteUserSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSubscriptionResponse>> getUserSubscriptions(@PathVariable UUID userId) {
        return ResponseEntity.ok(userSubscriptionService.getUserSubscriptions(userId));
    }
}
