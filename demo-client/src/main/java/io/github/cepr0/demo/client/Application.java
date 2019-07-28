package io.github.cepr0.demo.client;

import io.github.cepr0.demo.commons.dto.OrderRequest;
import io.github.cepr0.demo.commons.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Function;

@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@Bean
	public Function<Integer, ResponseEntity<OrderResponse>> restClient(RestTemplate restTemplate) {
		return (productId) -> restTemplate.postForEntity(
				"/",
				new OrderRequest(productId),
				OrderResponse.class
		);
	}

	@Bean
	public Function<Integer, ResponseEntity<OrderResponse>> fluxClient(WebClient webClient) {
		return (productId) -> webClient.post()
				.syncBody(new OrderRequest(productId))
				.exchange()
				.flatMap(r -> r.toEntity(OrderResponse.class))
				.block();
	}

	@Configuration
	static class ClientConfig {

		@LoadBalanced
		@Bean
		@ConditionalOnMissingBean(name = "webFluxConfigurer")
		public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
			return templateBuilder
					.rootUri("http://order-service/orders")
					.build();
		}

		@LoadBalanced
		@Bean
		WebClient webClient(LoadBalancerClient loadBalancerClient) {
			return WebClient.builder()
					.filter(new LoadBalancerExchangeFilterFunction(loadBalancerClient))
					.baseUrl("http://order-service/orders")
					.build();
		}
	}
}