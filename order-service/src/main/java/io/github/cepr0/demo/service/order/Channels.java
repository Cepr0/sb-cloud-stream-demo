package io.github.cepr0.demo.service.order;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String ORDER_COMPLETED = "order-completed";
	String ORDER_CREATED = "order-created";
	String PRODUCT_NOT_FOUND = "product-not-found";
	String PRODUCT_ENDED = "product-ended";

	@Input(ORDER_COMPLETED)
	SubscribableChannel orderCompleted();

	@Input(PRODUCT_NOT_FOUND)
	SubscribableChannel productNotFound();

	@Input(PRODUCT_ENDED)
	SubscribableChannel productEnded();

	@Output(ORDER_CREATED)
	MessageChannel orderCreated();
}
