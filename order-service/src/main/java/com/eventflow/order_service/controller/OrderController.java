package com.eventflow.order_service.controller;
import com.eventflow.order_service.entity.Order;
import com.eventflow.order_service.repository.OrderRepository;
import com.eventflow.order_service.service.OrderEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository repo;
    private OrderEventPublisher publisher;

    public OrderController(OrderRepository repo, OrderEventPublisher publisher){
        this.repo = repo;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Order> getAll() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order o) {
        o.setStatus("PLACED");
        Order saved = repo.save(o);
        publisher.publish(saved);
        return ResponseEntity.status(201).body(saved);
    }
}
