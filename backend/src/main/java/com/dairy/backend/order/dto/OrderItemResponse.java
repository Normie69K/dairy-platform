package com.dairy.backend.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemResponse {

    private UUID productId;
    private String productName;
    private int quantity;
    private BigDecimal price;

    public OrderItemResponse(
            UUID productId,
            String productName,
            int quantity,
            BigDecimal price
    ) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}
