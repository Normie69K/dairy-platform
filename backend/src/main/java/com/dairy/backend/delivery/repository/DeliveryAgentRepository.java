package com.dairy.backend.delivery.repository;

import com.dairy.backend.delivery.model.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, UUID> {

    List<DeliveryAgent> findByActiveTrue();
}
