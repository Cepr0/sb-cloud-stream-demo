package io.github.cepr0.demo.service.product;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String ORDER_CREATED = "order-created";
	String ORDER_COMPLETED = "order-completed";
	String ORDER_FAILED = "order-failed";

	@Input(ORDER_CREATED)
	SubscribableChannel orderCreated();

	@Output(ORDER_COMPLETED)
	MessageChannel orderCompleted();

	@Output(ORDER_FAILED)
	MessageChannel orderFailed();
}
