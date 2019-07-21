package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.event.OrderCompleted;
import io.github.cepr0.demo.commons.event.OrderCreated;
import io.github.cepr0.demo.commons.event.OrderFailed;
import io.github.cepr0.demo.commons.model.Order;
import io.github.cepr0.demo.commons.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.cepr0.demo.commons.model.Order.Status.COMPLETED;
import static io.github.cepr0.demo.commons.model.Order.Status.FAILED;
import static io.github.cepr0.demo.service.order.Channels.ORDER_CREATED;

@Slf4j
@EnableBinding(Channels.class)
public class OrderService {

	private final MessageChannel orderCreated;
	private final OrderRepo orderRepo;
	private final ProductRepo productRepo;
	private final ThreadLocalRandom random;

	public OrderService(
			@Qualifier(ORDER_CREATED) MessageChannel orderCreated,
			OrderRepo orderRepo,
			ProductRepo productRepo
	) {
		this.orderCreated = orderCreated;
		this.orderRepo = orderRepo;
		this.productRepo = productRepo;
		random = ThreadLocalRandom.current();
	}

	@Scheduled(fixedDelay = 1000)
	public void run() {
		productRepo.get(random.nextInt(1, productRepo.count()))
				.stream()
				.map(product -> Order.of(product.getId()))
				.map(orderRepo::save)
				.map(OrderCreated::new)
				.peek(orderCreatedEvent -> log.info("[i] Sent: {}", orderCreatedEvent))
				.map(GenericMessage::new)
				.findFirst()
				.map(orderCreated::send)
				.orElseThrow(IllegalStateException::new);
	}

	@StreamListener(Channels.ORDER_COMPLETED)
	public void markAsCompleted(OrderCompleted event) {
		log.info("[i] Received: {}", event);
		UUID orderId = event.getOrderId();
		orderRepo.update(orderId, COMPLETED)
				.ifPresentOrElse(
						order -> log.info("[i] Order marked as completed {}", order),
						() -> log.info("[i] Order with id {} not found", orderId)
				);
	}

	@StreamListener(Channels.ORDER_FAILED)
	public void markAsFailed(OrderFailed event) {
		log.info("[i] Received: {}", event);
		UUID orderId = event.getOrderId();
		orderRepo.update(orderId, FAILED)
				.ifPresentOrElse(
						order -> log.info("[i] Order marked as failed {}", order),
						() -> log.info("[i] Order with id {} not found", orderId)
				);
	}
}
