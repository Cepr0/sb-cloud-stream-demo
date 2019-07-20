package io.github.cepr0.demo.service.order;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String PRODUCTS = "products";
	String ORDERS = "orders";

	@Input(PRODUCTS)
	SubscribableChannel products();

	@Output(ORDERS)
	MessageChannel orders();
}
