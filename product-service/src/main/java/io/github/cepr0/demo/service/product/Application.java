package io.github.cepr0.demo.service.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EntityScan({
		"io.github.cepr0.demo.commons.model.base",
		"io.github.cepr0.demo.commons.model.product"
})
@EnableRetry
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class Application {

	private final ProductService productService;

	public Application(ProductService productService) {
		this.productService = productService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping("/products/{productId}/restock")
	public ResponseEntity restock(@PathVariable int productId, @RequestBody RestockRequest request) {
		return ResponseEntity.of(productService.restock(productId, request.getAmount()).map(RestockResponse::new));
	}
}
