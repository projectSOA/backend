package org.example.notificationservice.mappers;

import org.example.notificationservice.dtos.NotificationDTO;
import org.example.notificationservice.entities.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationDTO fromNotificationToNotificationDTO(Notification notification);
    Notification fromNotificationDTOToNotification(NotificationDTO notificationDTO);
}
