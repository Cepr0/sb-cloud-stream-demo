package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.dto.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
		SpringApplication.run(Application.class, args);
	}

	@PostMapping("/orders")
	public ResponseEntity createOrder(@RequestBody OrderRequest request) {
		int productId = request.getProductId();
		handler.send(productId);
		return ResponseEntity.accepted().build();
	}
}

