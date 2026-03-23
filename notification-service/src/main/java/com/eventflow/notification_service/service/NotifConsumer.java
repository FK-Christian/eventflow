package com.eventflow.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotifConsumer {

    @KafkaListener(topics = "order-placed", groupId = "notif-group")
    public void handle(String message) {
        String[] parts = message.split(":");
        String orderId   = parts[0];
        String productId = parts[1];
        System.out.println("[EMAIL] Commande " + orderId + " pour " + productId + " confirmée !");
    }
}
