package org.example.notificationservice.services.impl;

import org.example.notificationservice.dtos.NotificationDTO;
import org.example.notificationservice.entities.NotificationType;
import org.example.notificationservice.services.NotificationStrategy;

import java.util.Map;

public class DelayNotificationStrategy implements NotificationStrategy {

    @Override
    public void process(NotificationDTO notification) {
        Map<String, Object> info = notification.notificationInfo();

        String busNumber = (String) info.get("busNumber");
        Integer delayMinutes = (Integer) info.get("delayMinutes");
        String route = (String) info.get("route");

        String emailContent = String.format(
                "Your bus %s on route %s is delayed by %d minutes.",
                busNumber, route, delayMinutes
        );

        //sendEmail(notification.getRecipientEmail(), "Bus Delay Alert", emailContent);
    }

    @Override
    public boolean supports(NotificationType type) {
        return type == NotificationType.DELAY;
    }

    private void sendEmail(String email, String subject, String content) {
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
    }
}
