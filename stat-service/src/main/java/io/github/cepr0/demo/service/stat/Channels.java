package io.github.cepr0.demo.service.stat;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String ORDER_CREATED = "order-created";
	String ORDER_COMPLETED = "order-completed";
	String PRODUCT_NOT_FOUND = "product-not-found";
	String PRODUCT_ENDED = "product-ended";

	@Input(ORDER_CREATED)
	SubscribableChannel orderCreated();

	@Input(ORDER_COMPLETED)
	SubscribableChannel orderCompleted();

	@Input(PRODUCT_NOT_FOUND)
	SubscribableChannel productNotFound();

	@Input(PRODUCT_ENDED)
	SubscribableChannel productEnded();
}
