package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.dto.RestockRequest;
import io.github.cepr0.demo.commons.dto.RestockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.listener.RetryListenerSupport;
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
		new SpringApplicationBuilder(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@PostMapping(value = "/products/{productId}/restock", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity restock(@PathVariable int productId, @RequestBody RestockRequest request) {
		log.info("[i] Received {}", request);
		return ResponseEntity.of(
				productService.restock(productId, request.getVersion(), request.getAmount()).map(amount -> {
					log.info("[i] Product #{} is restocked: {}", productId, amount);
					return new RestockResponse(amount);
				})
		);
	}

	@Configuration
	public static class RetryConfig {
		@Bean
		public RetryListener listener() {
			return new RetryListenerSupport() {
				@Override
				public <T, E extends Throwable> void onError(RetryContext c, RetryCallback<T, E> cb, Throwable t) {
					log.warn("[w] Attempt failed due to: {}", t.toString());
				}
			};
		}
	}
}
