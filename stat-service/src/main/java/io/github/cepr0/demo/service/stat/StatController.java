package io.github.cepr0.demo.service.stat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@RestController
public class StatController {

	private final Flux<Map<String, Float>> eventStatsEmitter;

	public StatController(Flux<Map<String, Float>> eventStatsEmitter) {
		this.eventStatsEmitter = eventStatsEmitter;
	}

	@Bean
	public RouterFunction<ServerResponse> indexRouter(@Value("classpath:/static/index.html") Resource index) {
		return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(index));
	}


	@GetMapping("/stats")
	public Flux<ServerSentEvent> getStats() {
		return eventStatsEmitter
				.doOnNext(data -> log.info("[i] Sending: {}", data))
				.doOnSubscribe(s -> log.info("[i] New subscription"))
				.doOnCancel(() -> log.info("[i] Canceling..."))
				.map(data -> ServerSentEvent.builder(data).build());
	}
}
