package io.github.cepr0.demo.service.order;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String ORDER_COMPLETED = "order-completed";
	String ORDER_FAILED = "order-failed";
	String ORDER_CREATED = "order-created";

	@Input(ORDER_COMPLETED)
	SubscribableChannel orderCompleted();

	@Input(ORDER_FAILED)
	SubscribableChannel orderFailed();

	@Output(ORDER_CREATED)
	MessageChannel orderCreated();
}
