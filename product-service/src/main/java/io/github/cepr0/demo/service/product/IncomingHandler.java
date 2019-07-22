package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.event.Event;
import io.github.cepr0.demo.commons.event.OrderCompleted;
import io.github.cepr0.demo.commons.event.OrderCreated;
import io.github.cepr0.demo.commons.event.OrderFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.UUID;

@Slf4j
@EnableAsync
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
		UUID orderId = orderCreatedEvent.getOrder().getId();

		Event event = productService.sell(productId)
				.map(amount -> {
					if (amount > 0) {
						int balance = amount - 1;
						return OrderCompleted.of(orderId, balance);
					}
					return OrderFailed.productEnded(orderId, productId);
				})
				.orElse(OrderFailed.productNotFound(orderId, productId));

		outgoingHandler.send(event);
	}
}
