package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.event.OrderCompleted;
import io.github.cepr0.demo.commons.event.OrderFailedEvent;
import io.github.cepr0.demo.commons.event.ProductEnded;
import io.github.cepr0.demo.commons.event.ProductNotFound;
import io.github.cepr0.demo.commons.model.order.Reason;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@EnableBinding(Channels.class)
public class IncomingHandler {

	private final OrderService orderService;

	public IncomingHandler(OrderService orderService) {
		this.orderService = orderService;
	}

	@Async
	@StreamListener(Channels.ORDER_COMPLETED)
	public void handleOrderCompleted(OrderCompleted event) {
		log.info("[i] Received: {}", event);
		long orderId = event.getOrderId();
		orderService.markAsCompleted(orderId)
				.ifPresentOrElse(
						order -> log.info("[i] Order marked as completed: {}", order),
						() -> log.info("[i] Pending order with id '{}' not found", orderId)
				);
	}

	@Async
	@StreamListener(Channels.PRODUCT_NOT_FOUND)
	public void handleProductNotFound(ProductNotFound event) {
		markProductAsFailed(event, Reason.PRODUCT_NOT_FOUND);
	}

	@Async
	@StreamListener(Channels.PRODUCT_ENDED)
	public void handleProductEnded(ProductEnded event) {
		markProductAsFailed(event, Reason.PRODUCT_ENDED);
	}

	private void markProductAsFailed(OrderFailedEvent event, Reason reason) {
		log.info("[i] Received: {}", event);
		long orderId = event.getOrderId();
		orderService.markAsFailed(orderId, reason)
				.ifPresentOrElse(
						order -> log.info("[i] Order marked as failed: {}", order),
						() -> log.info("[i] Pending order with id '{}' not found", orderId)
				);
	}
}
