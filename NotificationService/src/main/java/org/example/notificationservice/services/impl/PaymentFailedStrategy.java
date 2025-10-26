package org.example.notificationservice.services.impl;

import org.example.notificationservice.dtos.NotificationDTO;
import org.example.notificationservice.entities.NotificationType;
import org.example.notificationservice.services.NotificationStrategy;

import java.util.Map;

public class PaymentFailedStrategy implements NotificationStrategy {


    @Override
    public void process(NotificationDTO notification) {
        Map<String, Object> info = notification.notificationInfo();

        String orderId = (String) info.get("orderId");
        Double amount = (Double) info.get("amount");
        String reason = (String) info.get("reason");

        String emailContent = String.format(
                "Payment failed for order %s. Amount: $%.2f. Reason: %s. Please update your payment method.",
                orderId, amount, reason
        );

        //sendEmail(notification.getRecipientEmail(), "Payment Failed", emailContent);
    }

    @Override
    public boolean supports(NotificationType type) {
        return type == NotificationType.PAYMENT_FAILED;
    }

    private void sendEmail(String email, String subject, String content) {
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
    }
}
