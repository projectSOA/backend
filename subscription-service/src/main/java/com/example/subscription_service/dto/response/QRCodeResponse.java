package com.example.subscription_service.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeResponse {
    private UUID userSubscriptionId;
    private String qrCodeImage; // Base64 encoded image
    private LocalDate expiryDate;
    private Integer remainingTickets;
}