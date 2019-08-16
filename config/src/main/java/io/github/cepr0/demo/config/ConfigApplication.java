package io.github.cepr0.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

@Slf4j
@EnableBinding(ConfigApplication.Routes.class)
@SpringBootApplication
public class ConfigApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args)
				.close();
	}

	public interface Routes {
		// to order-service
		@Input("order-completed-to-order-service")
		SubscribableChannel orderCompletedToOrderService();

		@Input("product-not-found-to-order-service")
		SubscribableChannel productNotFoundToOrderService();

		@Input("product-ended-to-order-service")
		SubscribableChannel productEndedToOrderService();

		// to product-service
		@Input("order-created-to-product-service")
		SubscribableChannel orderCreatedToProductService();

		// to supply-service
		@Input("product-ended-to-supply-service")
		SubscribableChannel productEndedToSupplyService();

		// to stat-service
		@Input("order-created-to-stat-service")
		SubscribableChannel orderCreatedToStatService();

		@Input("order-completed-to-stat-service")
		SubscribableChannel orderCompletedToStatService();

		@Input("product-not-found-to-stat-service")
		SubscribableChannel productNotFoundToStatService();

		@Input("product-ended-to-stat-service")
		SubscribableChannel productEndedToStatService();
	}
}
