package io.github.cepr0.demo.service.stat;

import io.github.cepr0.demo.commons.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.WorkQueueProcessor;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class StatConfig {

	private static final long FRAME_DURATION = 2;

	@Bean
	public FluxProcessor<Event, Event> eventProcessor() {
		return WorkQueueProcessor.create();
	}

	/**
	 * @return Stream of Maps of event names and their rate per second, eg: 'OrderCreated - 2.80/sec'
	 */
	@Bean
	public Flux<Map<String, Float>> eventStatsEmitter() {
		return eventProcessor()
				.window(Duration.ofSeconds(FRAME_DURATION))
				.flatMap(this::calcEventRates)
				.publish()
				.autoConnect()
				.publishOn(Schedulers.parallel());
	}

	Mono<Map<String, Float>> calcEventRates(Flux<Event> events) {
		return events
				.reduce(supplyEventMap(), (m, event) -> {
					m.computeIfPresent(event.getClass().getSimpleName(), (k, v) -> v + 1);
					return m;
				})
				.map(m -> m.entrySet()
						.stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() / (float) FRAME_DURATION))
				);
	}

	private Map<String, Integer> supplyEventMap() {
		Map<String, Integer> map = new HashMap<>();
		map.put(OrderCreated.class.getSimpleName(), 0);
		map.put(OrderCompleted.class.getSimpleName(), 0);
		map.put(ProductNotFound.class.getSimpleName(), 0);
		map.put(ProductEnded.class.getSimpleName(), 0);
		return map;
	};
}
