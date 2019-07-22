package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
