package io.github.cepr0.demo.service.stat;

import io.github.cepr0.demo.commons.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.WorkQueueProcessor;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class StatConfig {

	private static final long FRAME_DURATION = 10;

	@Bean
	public FluxProcessor<Event, Event> eventProcessor() {
		return WorkQueueProcessor.create();
	}

	/**
	 * @return Stream of Maps of event names and their rate per second, ie: 'OrderCreated - 2.80/sec'
	 */
	@Bean
	public Flux<Map<String, Float>> eventStatsEmitter() {
		return eventProcessor()
				.window(Duration.ofSeconds(FRAME_DURATION))
				.flatMap(this::calcEventRates)
				.publish()
				.autoConnect();
	}

	Mono<Map<String, Float>> calcEventRates(Flux<Event> events) {
		return events
				.reduce(new HashMap<String, Integer>(), (m, event) -> {
					m.compute(event.getClass().getSimpleName(), (k, v) -> v == null ? 1 : v + 1);
					return m;
				})
				.map(m -> m.entrySet()
						.stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() / (float) FRAME_DURATION))
				);
	}

	@Bean
	public void logSubscriber() {
		eventStatsEmitter().subscribe(map -> log.info("[i] Stat data: {}", map));
	}
}
