package io.github.cepr0.demo.service.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@Profile({"!dev", "!test"})
public class OrderGenerator {

	private static final int MAX_PRODUCT_ID = 10;
	private static final int NOT_EXISTED_PRODUCT_NUMBER = 3;

	private final OutgoingHandler outgoingHandler;

	public OrderGenerator(OutgoingHandler outgoingHandler) {
		this.outgoingHandler = outgoingHandler;
	}

	@Async
	@Scheduled(fixedRate = 500)
	public void generate() {
		var random = ThreadLocalRandom.current();
		int productId = random.nextInt(1, MAX_PRODUCT_ID + 1 + NOT_EXISTED_PRODUCT_NUMBER);
		outgoingHandler.send(productId);
	}
}
