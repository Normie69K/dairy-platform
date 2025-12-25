package com.dairy.backend.delivery.controller;

import com.dairy.backend.delivery.model.DeliveryAgent;
import com.dairy.backend.delivery.repository.DeliveryAgentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-agents")
public class DeliveryAgentController {

    private final DeliveryAgentRepository repository;

    public DeliveryAgentController(DeliveryAgentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<DeliveryAgent> create(@RequestBody DeliveryAgent agent) {
        return ResponseEntity.ok(repository.save(agent));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(repository.findAll());
    }
}
