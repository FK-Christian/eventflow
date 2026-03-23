package com.eventflow.inventory_service.controller;
import com.eventflow.inventory_service.entity.StockItem;
import com.eventflow.inventory_service.repository.StockRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {
    private StockRepository repo;

    public StockController(StockRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public List<StockItem> getAll() { return repo.findAll(); }

    @GetMapping("/{id}")
    public StockItem get(@PathVariable String id) {
        return repo.findById(id).orElseThrow();
    }
}
