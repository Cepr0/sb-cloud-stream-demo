package io.github.cepr0.demo.service.stat;

import io.github.cepr0.demo.commons.event.*;
import io.github.cepr0.demo.commons.model.order.Order;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Map;

import static io.github.cepr0.demo.service.stat.StatConfig.FRAME_DURATION;

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
						OrderCreated.class.getSimpleName(), 2 / (float) FRAME_DURATION,
						OrderCompleted.class.getSimpleName(), 2 / (float) FRAME_DURATION,
						ProductNotFound.class.getSimpleName(), 0F,
						ProductEnded.class.getSimpleName(), 0F
				))
				.expectNextCount(0)
				.verifyComplete();
	}
}