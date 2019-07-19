package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.Order;
import io.github.cepr0.demo.commons.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@EnableBinding(Channel.class)
@SpringBootApplication
public class OrderServiceApplication {

	private static final AtomicInteger COUNTER = new AtomicInteger();

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	@InboundChannelAdapter(channel = Channel.ORDERS, poller = @Poller(fixedDelay = "1000"))
	public MessageSource<Order> sendOrder() {
		return () -> {
			int i = COUNTER.incrementAndGet();
			Order order = new Order(i, "order-" + i);
			log.info("[i] Sent: {}", order);
			return new GenericMessage<>(order);
		};
	}

	@StreamListener(Channel.PRODUCTS)
	public void receiveProduct(Product product) {
		log.info("[i] Received: {}", product);
	}
}
