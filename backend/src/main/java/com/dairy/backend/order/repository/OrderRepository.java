package com.dairy.backend.order.repository;

import com.dairy.backend.order.model.Order;
import com.dairy.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUser(User user);
}
