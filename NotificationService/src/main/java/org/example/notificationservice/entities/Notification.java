package org.example.notificationservice.entities;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Document(collection = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    @Id
    private String id = UUID.randomUUID().toString();

    private Map<String, Object> notificationInfo;

    private String title;

    private String message;

    private LocalDateTime sentAt;

    private LocalDateTime scheduledFor;

    private Boolean sent;

    private NotificationType type;
}
