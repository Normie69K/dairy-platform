package com.dairy.backend.order.dto;

import com.dairy.backend.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class OrderSummaryResponse {

    private UUID id;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Instant createdAt;

    public OrderSummaryResponse(
            UUID id,
            OrderStatus status,
            BigDecimal totalAmount,
            Instant createdAt
    ) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Instant getCreatedAt() { return createdAt; }
}
