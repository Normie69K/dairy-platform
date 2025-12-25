package com.dairy.backend.order.service;

import com.dairy.backend.delivery.model.DeliveryAgent;
import com.dairy.backend.delivery.repository.DeliveryAgentRepository;
import com.dairy.backend.order.model.Order;
import com.dairy.backend.order.model.OrderStatus;
import com.dairy.backend.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderAssignmentService {

    private final OrderRepository orderRepository;
    private final DeliveryAgentRepository agentRepository;

    public OrderAssignmentService(
            OrderRepository orderRepository,
            DeliveryAgentRepository agentRepository
    ) {
        this.orderRepository = orderRepository;
        this.agentRepository = agentRepository;
    }

    public Order assign(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Order not assignable");
        }

        List<DeliveryAgent> agents = agentRepository.findByActiveTrue();
        if (agents.isEmpty()) {
            throw new IllegalStateException("No delivery agents available");
        }

        DeliveryAgent agent = agents.get(0);

        order.setDeliveryAgent(agent);
        order.setStatus(OrderStatus.ASSIGNED);

        return orderRepository.save(order);
    }
}
