package io.github.cepr0.demo.service.product;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channel {

	String ORDERS = "orders";
	String PRODUCTS = "products";

	@Input(ORDERS)
	SubscribableChannel orders();

	@Output(PRODUCTS)
	MessageChannel products();
}
