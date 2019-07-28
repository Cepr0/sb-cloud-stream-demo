package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.event.OrderCreated;
import io.github.cepr0.demo.commons.model.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static io.github.cepr0.demo.service.order.Channels.ORDER_CREATED;

@Slf4j
@Component
public class OutgoingHandler {

	private final OrderService orderService;
	private final MessageChannel channel;
	private final ApplicationEventPublisher publisher;

	public OutgoingHandler(
			OrderService orderService,
			@Qualifier(ORDER_CREATED) MessageChannel channel,
			ApplicationEventPublisher publisher
	) {
		this.orderService = orderService;
		this.channel = channel;
		this.publisher = publisher;
	}

	@Transactional
	public Order send(int productId) {
		Order order = orderService.create(productId);
		var event = new OrderCreated(order);
		publisher.publishEvent(event);
		return order;
	}

	@TransactionalEventListener
	public void send(OrderCreated event) {
		var message = new GenericMessage<>(event);
		channel.send(message);
		log.info("[i] Sent: {}", event);
	}
}
