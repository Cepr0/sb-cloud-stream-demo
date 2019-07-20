package io.github.cepr0.demo.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Routes {
	@Input("products-to-order-service") SubscribableChannel productsToOrderService();
	@Input("orders-to-product-service") SubscribableChannel ordersToProductService();
}
