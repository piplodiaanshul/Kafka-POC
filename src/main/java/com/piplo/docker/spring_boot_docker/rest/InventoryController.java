package com.piplo.docker.spring_boot_docker.rest;

import com.piplo.docker.spring_boot_docker.groceries.GroceryItem;
import com.piplo.docker.spring_boot_docker.groceries.ItemRepository;
import com.piplo.docker.spring_boot_docker.kafka.poc.GroceryProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class InventoryController {

    private static final boolean IS_KAFKA_ON = true;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private GroceryProducers groceryProducers;

    @GetMapping("/test")
    public ResponseEntity<?> getStuff() {
        return ResponseEntity.ok("working");
    }

    @GetMapping("/groceries/all")
    public ResponseEntity<?> getGroceries() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @PostMapping("/groceries")
    public ResponseEntity<?> getGroceries(@RequestBody GroceryItem groceryItem) {
        if (IS_KAFKA_ON) {
            groceryProducers.producer(List.of(groceryItem));
            return ResponseEntity.accepted().body("Item will be saved!");
        }
        return ResponseEntity.ok(itemRepository.save(groceryItem));
    }

}
