package org.example.notificationservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID routeId; // I might remove it to something more general like a json with infos

    private String title;

    private String message;

    private LocalDateTime sentAt;

    private LocalDateTime scheduledFor;

    private Boolean sent;

    private NotificationType type;
}
