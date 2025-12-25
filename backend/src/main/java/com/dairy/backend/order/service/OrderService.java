package com.dairy.backend.order.service;

import com.dairy.backend.order.model.Order;

import java.util.Map;
import java.util.UUID;

public interface OrderService {

    Order createOrder(UUID userId, Map<UUID, Integer> productQuantities);
}
