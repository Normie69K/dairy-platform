package com.dairy.backend.order.controller;

import com.dairy.backend.order.dto.OrderDetailResponse;
import com.dairy.backend.order.dto.OrderSummaryResponse;
import com.dairy.backend.order.model.Order;
import com.dairy.backend.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderSummaryResponse>> myOrders(
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        List<OrderSummaryResponse> response =
                orderService.getOrdersForUser(userId)
                        .stream()
                        .map(o -> new OrderSummaryResponse(
                                o.getId(),
                                o.getStatus(),
                                o.getTotalAmount(),
                                o.getCreatedAt()
                        ))
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrder(
            @PathVariable UUID orderId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        Order order = orderService.getOrderForUser(orderId, userId);

        OrderDetailResponse response = OrderDetailResponse.from(order);
        return ResponseEntity.ok(response);
    }


    /*@PostMapping
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
    }*/
}
