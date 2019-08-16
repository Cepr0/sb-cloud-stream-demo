package io.github.cepr0.demo.service.stat;

import io.github.cepr0.demo.commons.event.Event;
import io.github.cepr0.demo.commons.event.OrderCompleted;
import io.github.cepr0.demo.commons.event.OrderCreated;
import io.github.cepr0.demo.commons.model.order.Order;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Map;

public class StatConfigTest {

	@Test
	public void countEvents() {
		Flux<Event> events = Flux.just(
				new OrderCreated(Order.of(1)),
				new OrderCreated(Order.of(2)),
				OrderCompleted.of(1, 99),
				OrderCompleted.of(2, 98)
		);

		StepVerifier.create(new StatConfig().calcEventRates(events))
				.expectNext(Map.of(
						"OrderCreated", 0.2F,
						"OrderCompleted", 0.2F
				))
				.expectNextCount(0)
				.verifyComplete();
	}
}