package io.github.cepr0.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

@Slf4j
@EnableBinding(ConfigApplication.Routes.class)
@SpringBootApplication
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication
				.run(ConfigApplication.class, args)
				.close();
	}

	public interface Routes {
		// to order-service
		@Input("order-completed-to-order-service")
		SubscribableChannel orderCompletedToOrderService();

		@Input("order-failed-to-order-service")
		SubscribableChannel orderFailedToOrderService();

		// to product-service
		@Input("order-created-to-product-service")
		SubscribableChannel orderCreatedToProductService();

		// to supply-service
		@Input("order-failed-to-supply-service")
		SubscribableChannel orderFailedToSupplyService();
	}
}
