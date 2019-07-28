package io.github.cepr0.demo.client;

import io.github.cepr0.demo.commons.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@Slf4j
@Component
public class OrderClient {

	private static final int MAX_PRODUCT_ID = 10;
	private static final int NOT_EXISTED_PRODUCT_NUMBER = 3;

	private final Function<Integer, ResponseEntity<OrderResponse>> httpClient;
	private final TaskScheduler scheduler;
	private final AppProps props;

	public OrderClient(
			@Qualifier("fluxClient") Function<Integer, ResponseEntity<OrderResponse>> httpClient,
			TaskScheduler scheduler,
			AppProps props
	) {
		this.httpClient = httpClient;
		this.scheduler = scheduler;
		this.props = props;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {
		scheduler.schedule(
				this::createOrder,
				new PeriodicTrigger(props.getInterval().toMillis())
		);
	}

	private void createOrder() {

		var random = ThreadLocalRandom.current();
		int productId = random.nextInt(1, MAX_PRODUCT_ID + 1 + NOT_EXISTED_PRODUCT_NUMBER);

		ResponseEntity<OrderResponse> response = httpClient.apply(productId);

		Optional.ofNullable(response.getBody()).ifPresentOrElse(
				orderResponse -> log.info("[i] Order created #{}", orderResponse.getOrderId()),
				() -> log.error("[!] Response error: {}", response.getStatusCode())
		);
	}
}
