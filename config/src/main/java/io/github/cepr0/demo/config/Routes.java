package io.github.cepr0.demo.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Routes {
	// to order-service
	@Input("order-completed-to-order-service") SubscribableChannel orderCompletedToOrderService();
	@Input("order-failed-to-order-service") SubscribableChannel orderFailedToOrderService();
	// to product-service
	@Input("order-created-to-product-service") SubscribableChannel orderCreatedToProductService();
}
