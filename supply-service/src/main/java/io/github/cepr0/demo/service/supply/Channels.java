package io.github.cepr0.demo.service.supply;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

	String PRODUCT_ENDED = "product-ended";

	@Input(PRODUCT_ENDED)
	SubscribableChannel productEnded();
}
