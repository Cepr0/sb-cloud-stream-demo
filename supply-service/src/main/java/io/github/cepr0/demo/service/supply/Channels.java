package io.github.cepr0.demo.service.supply;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String ORDER_FAILED = "order-failed";

	@Input(ORDER_FAILED)
	SubscribableChannel orderFailed();
}
