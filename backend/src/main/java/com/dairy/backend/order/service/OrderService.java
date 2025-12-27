package com.dairy.backend.order.service;

import com.dairy.backend.order.model.Order;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    Order createOrder(UUID userId, Map<UUID, Integer> productQuantities);

    List<Order> getOrdersForUser(UUID userId);

    Order getOrderForUser(UUID orderId, UUID userId);

}
