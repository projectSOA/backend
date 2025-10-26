package org.example.notificationservice.dtos;

import org.example.notificationservice.entities.NotificationType;

import java.time.LocalDateTime;
import java.util.Map;


public record NotificationDTO(
        String id,
        String title,
        Map<String, Object> notificationInfo,
        NotificationType type,
        String message,
        LocalDateTime sentAt,
        LocalDateTime scheduledFor,
        Boolean sent
) {
}
