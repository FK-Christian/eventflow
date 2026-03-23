package com.eventflow.order_service.service;

import com.eventflow.order_service.entity.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

    private KafkaTemplate<String, String> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(Order order) {
        String msg = order.getId() + ":" + order.getProductId()
                + ":" + order.getQuantity();
        kafkaTemplate.send("order-placed", msg);
        System.out.println("Event published: " + msg);
    }
}
