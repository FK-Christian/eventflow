package com.eventflow.inventory_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class StockItem {
    @Id private String productId;
    private Integer quantity = 100;
}
