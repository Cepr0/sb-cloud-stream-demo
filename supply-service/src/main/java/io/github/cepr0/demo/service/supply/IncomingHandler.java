package io.github.cepr0.demo.service.supply;

import io.github.cepr0.demo.commons.event.ProductEnded;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@EnableBinding(Channels.class)
public class IncomingHandler {

	private final ProductClient productClient;

	public IncomingHandler(ProductClient productClient) {
		this.productClient = productClient;
	}

	@Async
	@StreamListener(Channels.PRODUCT_ENDED)
	public void restockProduct(ProductEnded event) {
		log.info("[i] Received: {}", event);

		int productId = event.getProductId();
		int version = event.getVersion();
		productClient.restock(productId, version).ifPresentOrElse(
				amount -> log.info("[i] Product #{} restocked: {}", productId, amount),
				() -> log.info("[i] Couldn't restock Product #{}", productId)
		);
	}
}
