package com.example.subscription_service.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserSubscriptionResponse {
    private UUID userSubscriptionId;
    private UUID userId;
    private String qrCode; // Base64 QR code
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalTickets;
    private String message; // "Subscription created successfully"
}
