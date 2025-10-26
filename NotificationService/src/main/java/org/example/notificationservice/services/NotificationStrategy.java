package org.example.notificationservice.services;

import org.example.notificationservice.dtos.NotificationDTO;
import org.example.notificationservice.entities.NotificationType;

public interface NotificationStrategy {
    void process(NotificationDTO notification);
    boolean supports(NotificationType type);
}
