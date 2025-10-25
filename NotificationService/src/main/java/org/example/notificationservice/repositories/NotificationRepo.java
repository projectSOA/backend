package org.example.notificationservice.repositories;

import org.example.notificationservice.entities.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepo extends MongoRepository<Notification, String> {
}
