package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {
	@Query("select o from Order o where o.status = 'PENDING' and o.id = ?1")
	Optional<Order> getPendingById(long orderId);
}
