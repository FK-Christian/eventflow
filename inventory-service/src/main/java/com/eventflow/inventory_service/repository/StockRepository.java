package com.eventflow.inventory_service.repository;

import com.eventflow.inventory_service.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockItem, String> {}
