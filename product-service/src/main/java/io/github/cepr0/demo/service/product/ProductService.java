package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.event.Event;
import io.github.cepr0.demo.commons.event.OrderCompleted;
import io.github.cepr0.demo.commons.event.OrderCreated;
import io.github.cepr0.demo.commons.event.OrderFailed;
import io.github.cepr0.demo.commons.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.GenericMessage;

import java.util.UUID;

@Slf4j
@EnableBinding(Channels.class)
public class ProductService {

	private final ProductRepo productRepo;
	private final Channels channels;

	public ProductService(ProductRepo productRepo, Channels channels) {
		this.productRepo = productRepo;
		this.channels = channels;
	}

	@StreamListener(Channels.ORDER_CREATED)
	public void handle(OrderCreated orderCreatedEvent) {
		log.info("[i] Received: {}", orderCreatedEvent);

		int productId = orderCreatedEvent.getOrder().getProductId();
		UUID orderId = orderCreatedEvent.getOrder().getId();

		Event outboundEvent = productRepo.get(productId)
				.map(product -> productRepo.sell(productId)
						.map(p -> (Event) OrderCompleted.of(orderId))
						.orElse(OrderFailed.productEnded(orderId, productId)))
				.orElse(OrderFailed.productNotFound(orderId, productId));

		log.info("[i] Sending: {}", outboundEvent);
		send(outboundEvent);
	}

	void send(Event event) {
		var message = new GenericMessage<>(event);

		if (event instanceof OrderCompleted) {
			channels.orderCompleted().send(message);
		}

		if (event instanceof OrderFailed) {
			channels.orderFailed().send(message);
		}
	}
}
