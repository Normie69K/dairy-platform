package com.dairy.backend.order.controller;

import com.dairy.backend.order.dto.CreateOrderRequest;
import com.dairy.backend.order.dto.OrderResponse;
import com.dairy.backend.order.model.Order;
import com.dairy.backend.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @RequestBody CreateOrderRequest request,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        Order order = orderService.createOrder(userId, request.getItems());

        return ResponseEntity.ok(
                new OrderResponse(
                        order.getId(),
                        order.getStatus(),
                        order.getTotalAmount(),
                        order.getCreatedAt()
                )
        );
    }
}
