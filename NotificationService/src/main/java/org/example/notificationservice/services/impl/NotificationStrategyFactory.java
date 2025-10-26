package org.example.notificationservice.services.impl;

import lombok.AllArgsConstructor;
import org.example.notificationservice.entities.NotificationType;
import org.example.notificationservice.services.NotificationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificationStrategyFactory {
    private final List<NotificationStrategy> strategies;


    public NotificationStrategy getNotificationStrategy(NotificationType notificationType) {
        return strategies.stream()
                .filter(strategy ->strategy.supports(notificationType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No strategy found for notification type: " + notificationType
                ));
    }
}
