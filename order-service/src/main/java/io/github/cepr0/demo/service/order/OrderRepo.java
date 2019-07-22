package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {
	@Query("select o from Order o where o.status = 'PENDING' and o.id = ?1")
	Optional<Order> getPendingById(UUID orderId);
}
