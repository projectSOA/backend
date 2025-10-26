package org.example.notificationservice.services.impl;

import org.example.notificationservice.dtos.NotificationDTO;
import org.example.notificationservice.entities.NotificationType;
import org.example.notificationservice.services.NotificationStrategy;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TripCancellationStrategy implements NotificationStrategy {

    @Override
    public void process(NotificationDTO notification) {
        Map<String, Object> info = notification.notificationInfo();

        String tripId = (String) info.get("tripId");
        String route = (String) info.get("route");
        String refundAmount = (String) info.get("refundAmount");

        String emailContent = String.format(
                "Your trip %s on route %s has been cancelled. Refund of %s will be processed within 3-5 business days.",
                tripId, route, refundAmount
        );

        //sendEmail(notification.getRecipientEmail(), "Trip Cancellation", emailContent);
    }

    @Override
    public boolean supports(NotificationType type) {
        return type == NotificationType.TRIP_CANCELLATION;
    }

    private void sendEmail(String email, String subject, String content) {
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
    }

}
