package org.example.notificationservice.services.impl;

import org.example.notificationservice.dtos.NotificationDTO;
import org.example.notificationservice.entities.Notification;
import org.example.notificationservice.services.RedisQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RedisQueueServiceImpl implements RedisQueueService {

    @Autowired
    private RedisTemplate<String, NotificationDTO> redisTemplate;

    private static final String QUEUE_KEY = "notification:queue";

    public void addNotification(NotificationDTO notification) {
        redisTemplate.opsForList().rightPush(QUEUE_KEY, notification);
    }

    @Scheduled(fixedDelay = 1000)
    public void processQueue() {
        NotificationDTO notification = redisTemplate.opsForList().leftPop(QUEUE_KEY);
        if (notification != null) {
            processNotification(notification);
        }
    }

    // this function should send email to all the users for example who have a delay payment or what
    // for example if in the info there is purpose called delay of a bus
    private void processNotification(NotificationDTO notification) {
        System.out.println("Processing: " + notification);
    }
}
