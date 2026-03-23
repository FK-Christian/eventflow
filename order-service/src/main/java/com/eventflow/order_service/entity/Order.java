package com.eventflow.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity @Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String productId;
    private Integer quantity;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
