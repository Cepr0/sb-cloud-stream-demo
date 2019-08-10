package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.dto.OrderRequest;
import io.github.cepr0.demo.commons.dto.OrderResponse;
import io.github.cepr0.demo.commons.model.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableAsync
@EnableScheduling
@EntityScan({
		"io.github.cepr0.demo.commons.model.base",
		"io.github.cepr0.demo.commons.model.order"
})
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class Application {

	private final OutgoingHandler handler;

	public Application(OutgoingHandler handler) {
		this.handler = handler;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity createOrder(@RequestBody OrderRequest request) {
		int productId = request.getProductId();
		Order order = handler.send(productId);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new OrderResponse(order));
	}
}

