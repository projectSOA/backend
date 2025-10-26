package org.example.notificationservice.dtos;

import org.example.notificationservice.entities.NotificationType;

import java.time.LocalDateTime;


public record NotificationDTO(
        String id,
        String title,
        NotificationType type,
        String message,
        LocalDateTime sentAt,
        LocalDateTime scheduledFor,
        Boolean sent
) {
}
