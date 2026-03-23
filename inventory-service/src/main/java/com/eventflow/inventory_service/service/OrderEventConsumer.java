package com.eventflow.inventory_service.service;
import com.eventflow.inventory_service.entity.StockItem;
import com.eventflow.inventory_service.repository.StockRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private StockRepository repo;

    public OrderEventConsumer(StockRepository repo){
        this.repo = repo;
    }

    @KafkaListener(topics = "order-placed", groupId = "inventory-group")
    public void handle(String message) {
        // message format: orderId:productId:qty
        String[] parts = message.split(":");
        String productId = parts[1];
        int qty = Integer.parseInt(parts[2]);

        StockItem item = repo.findById(productId)
                .orElseGet(() -> {
                    StockItem s = new StockItem();
                    s.setProductId(productId);
                    s.setQuantity(100);
                    return s;
                });
        item.setQuantity(item.getQuantity() - qty);
        repo.save(item);
        System.out.println("Stock updated: " + productId + " → " + item.getQuantity());
    }
}
