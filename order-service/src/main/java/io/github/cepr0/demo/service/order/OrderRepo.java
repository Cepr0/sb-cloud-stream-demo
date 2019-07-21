package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.cepr0.demo.commons.model.Order.Status;

@Slf4j
@Component
public class OrderRepo {

	// ID and the Order
	private final Map<UUID, Order> orders = new ConcurrentHashMap<>();

	public Order save(Order order) {
		orders.put(order.getId(), order);
		return order;
	}

	public Optional<Order> get(UUID orderId) {
		return Optional.ofNullable(orders.get(orderId));
	}

	public Optional<Order> update(UUID orderId, Status status) {
		return get(orderId).map(order -> {
			switch (status) {
				case PENDING:
					throw new IllegalArgumentException();
				case COMPLETED:
				case FAILED:
					if (order.getStatus() != Status.PENDING) {
						throw new IllegalArgumentException();
					}
			}
			orders.replace(orderId, order.setStatus(status));
			log.info("[i] Order updated {}", order);
			return order;
		});
	}

	public Collection<Order> getAll() {
		return orders.values();
	}

	public int count() {
		return orders.size();
	}
}
