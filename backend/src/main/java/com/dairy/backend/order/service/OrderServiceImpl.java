package com.dairy.backend.order.service;

import com.dairy.backend.order.model.Order;
import com.dairy.backend.order.model.OrderItem;
import com.dairy.backend.order.model.OrderStatus;
import com.dairy.backend.order.repository.OrderItemRepository;
import com.dairy.backend.order.repository.OrderRepository;
import com.dairy.backend.product.model.Product;
import com.dairy.backend.product.repository.ProductRepository;
import com.dairy.backend.user.model.User;
import com.dairy.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Order createOrder(UUID userId, Map<UUID, Integer> productQuantities) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<UUID, Integer> entry : productQuantities.entrySet()) {

            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalStateException("Product not found"));

            int quantity = entry.getValue();
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice()); // snapshot

            orderItemRepository.save(item);

            total = total.add(
                    product.getPrice().multiply(BigDecimal.valueOf(quantity))
            );
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersForUser(UUID userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Order getOrderForUser(UUID orderId, UUID userId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));
    }
}
