package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.scheduling.annotation.Async;

@Slf4j
// @EnableAsync
@EnableBinding(Channels.class)
public class IncomingHandler {

	private final ProductService productService;
	private final OutgoingHandler outgoingHandler;

	public IncomingHandler(ProductService productService, OutgoingHandler outgoingHandler) {
		this.productService = productService;
		this.outgoingHandler = outgoingHandler;
	}

	@Async
	@StreamListener(Channels.ORDER_CREATED)
	public void handleOrderCreated(OrderCreated orderCreatedEvent) {
		log.info("[i] Received: {}", orderCreatedEvent);

		int productId = orderCreatedEvent.getOrder().getProductId();
		long orderId = orderCreatedEvent.getOrder().getId();

		Event event = productService.sell(orderId, productId)
				.map(productAmount -> {
					int amount = productAmount.getAmount();
					if (amount > 0) {
						log.info("[i] Order #{} for product#{} competed.", orderId, productId);
						return OrderCompleted.of(orderId, amount - 1);
					} else {
						return ProductEnded.of(orderId, productId, productAmount.getVersion());
					}
				})
				.orElse(ProductNotFound.of(orderId, productId));

		outgoingHandler.send(event);
	}
}
