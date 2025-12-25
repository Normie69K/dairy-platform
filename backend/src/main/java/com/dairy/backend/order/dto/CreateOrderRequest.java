package com.dairy.backend.order.dto;

import java.util.Map;
import java.util.UUID;

public class CreateOrderRequest {

    // productId -> quantity
    private Map<UUID, Integer> items;

    public Map<UUID, Integer> getItems() {
        return items;
    }

    public void setItems(Map<UUID, Integer> items) {
        this.items = items;
    }
}
