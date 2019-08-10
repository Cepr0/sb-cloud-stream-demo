package io.github.cepr0.demo.commons.event;

import lombok.Value;

import java.time.Instant;

@Value
public class ProductEnded implements OrderFailedEvent {
	private long orderId;
	private int productId;
	private int version;
	private Instant at;

	public static ProductEnded of(long orderId, int productId, int version) {
		return new ProductEnded(orderId, productId, version, Instant.now());
	}
}
