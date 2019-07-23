package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.model.order.Order;
import io.github.cepr0.demo.commons.model.order.Reason;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class OrderService {

	private final OrderRepo orderRepo;

	public OrderService(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}

	public Order create(int productId) {
		Order order = orderRepo.save(Order.of(productId));
		log.info("[i] Order saved {}", order);
		return order;
	}

	public Optional<Order> markAsCompleted(long orderId) {
		return orderRepo.getPendingById(orderId)
				.map(Order::markAsCompleted)
				.map(order -> {
					log.info("[i] Order updated {}", order);
					return order;
				});
	}

	public Optional<Order> markAsFailed(long orderId, Reason reason) {
		return orderRepo.getPendingById(orderId)
				.map(order -> order.markAsFailed(reason))
				.map(order -> {
					log.info("[i] Order updated {}", order);
					return order;
				});
	}
}
