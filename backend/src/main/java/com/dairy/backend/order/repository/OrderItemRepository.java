package com.dairy.backend.order.repository;

import com.dairy.backend.order.model.Order;
import com.dairy.backend.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    List<OrderItem> findByOrder(Order order);
}
