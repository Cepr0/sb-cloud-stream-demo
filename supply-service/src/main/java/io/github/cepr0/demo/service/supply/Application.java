package io.github.cepr0.demo.service.supply;

import io.github.cepr0.demo.commons.TriFunction;
import io.github.cepr0.demo.commons.dto.RestockRequest;
import io.github.cepr0.demo.commons.dto.RestockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@Bean
	public TriFunction<Integer, Integer, Integer, ResponseEntity<RestockResponse>> funRestockRest(RestTemplate restTemplate) {
		return (productId, amount, version) -> restTemplate.postForEntity(
				"/{productId}/restock",
				new RestockRequest(amount, version),
				RestockResponse.class,
				productId
		);
	}

	@Bean
	public TriFunction<Integer, Integer, Integer, ResponseEntity<RestockResponse>> funRestock(WebClient webClient) {
		return (productId, amount, version) -> webClient.post()
				.uri("/{productId}/restock", productId)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.syncBody(new RestockRequest(amount, version))
				.exchange()
				.flatMap(r -> r.toEntity(RestockResponse.class))
				.block();
	}

	@Configuration
	static class ClientConfig {

		@LoadBalanced
		@Bean
		public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
			return templateBuilder
					.rootUri("http://product-service/products")
					.build();
		}

		@LoadBalanced
		@Bean
		WebClient webClient(LoadBalancerClient loadBalancerClient) {
			return WebClient.builder()
					.filter(new LoadBalancerExchangeFilterFunction(loadBalancerClient))
					.baseUrl("http://product-service/products")
					.build();
		}
	}
}