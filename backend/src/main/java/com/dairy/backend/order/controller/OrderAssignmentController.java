package com.dairy.backend.order.controller;

import com.dairy.backend.order.model.Order;
import com.dairy.backend.order.service.OrderAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderAssignmentController {

    private final OrderAssignmentService service;

    public OrderAssignmentController(OrderAssignmentService service) {
        this.service = service;
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<Order> assign(@PathVariable UUID id) {
        return ResponseEntity.ok(service.assign(id));
    }
}
