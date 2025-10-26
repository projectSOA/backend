package org.example.notificationservice.services;

import org.example.notificationservice.dtos.NotificationDTO;

public interface RedisQueueService {

    void addNotification(NotificationDTO notification);
    void processQueue();
}
