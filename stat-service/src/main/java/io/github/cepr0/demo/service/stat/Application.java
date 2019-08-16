package io.github.cepr0.demo.service.stat;

import io.github.cepr0.demo.commons.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;

@Slf4j
@EnableBinding(Channels.class)
@SpringBootApplication
public class Application {

	private final FluxProcessor<Event, Event> eventProcessor;

	public Application(FluxProcessor<Event, Event> eventProcessor) {
		this.eventProcessor = eventProcessor;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@StreamListener(Channels.ORDER_CREATED)
	public void handleOrderCreatedEvent(Flux<OrderCreated> stream) {
		subscribeEvent(stream);
	}

	@StreamListener(Channels.ORDER_COMPLETED)
	public void handleOrderCompletedEvent(Flux<OrderCompleted> stream) {
		subscribeEvent(stream);
	}

	@StreamListener(Channels.PRODUCT_NOT_FOUND)
	public void handleProductNotFoundEvent(Flux<ProductNotFound> stream) {
		subscribeEvent(stream);
	}

	@StreamListener(Channels.PRODUCT_ENDED)
	public void handleProductEndedEvent(Flux<ProductEnded> stream) {
		subscribeEvent(stream);
	}

	private void subscribeEvent(Flux<? extends Event> stream) {
		stream
				// .doOnNext(event -> log.info("[i] Received: {}", event))
				.subscribe(eventProcessor::onNext);
	}
}
