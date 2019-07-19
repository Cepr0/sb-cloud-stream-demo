package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.Order;
import io.github.cepr0.demo.commons.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@Slf4j
@EnableBinding(Channel.class)
@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@StreamListener(Channel.ORDERS)
	@SendTo(Channel.PRODUCTS)
	public Product receiveOrder(Order order) {
		log.info("[i] Received: {}", order);
		Integer orderNum = order.getNum();
		Product product = new Product(orderNum, "product-" + orderNum, orderNum);
		log.info("[i] Sending: {}", product);
		return product;
	}
}
