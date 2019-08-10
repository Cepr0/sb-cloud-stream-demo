package io.github.cepr0.demo.service.product;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String ORDER_CREATED = "order-created";
	String ORDER_COMPLETED = "order-completed";
	String PRODUCT_NOT_FOUND = "product-not-found";
	String PRODUCT_ENDED = "product-ended";

	@Input(ORDER_CREATED)
	SubscribableChannel orderCreated();

	@Output(ORDER_COMPLETED)
	MessageChannel orderCompleted();

	@Output(PRODUCT_NOT_FOUND)
	MessageChannel productNotFound();

	@Output(PRODUCT_ENDED)
	MessageChannel productEnded();
}
