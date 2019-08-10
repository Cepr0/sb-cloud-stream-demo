package io.github.cepr0.demo.commons.event;

import lombok.Value;

import java.time.Instant;

@Value
public class ProductNotFound implements OrderFailedEvent {
	private long orderId;
	private int productId;
	private Instant at;

	public static ProductNotFound of(long orderId, int productId) {
		return new ProductNotFound(orderId, productId, Instant.now());
	}
}
