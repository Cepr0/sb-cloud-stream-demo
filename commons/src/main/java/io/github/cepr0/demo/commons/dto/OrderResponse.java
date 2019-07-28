package io.github.cepr0.demo.commons.dto;

import io.github.cepr0.demo.commons.model.order.Order;
import lombok.Value;
import lombok.experimental.Tolerate;

import java.time.Instant;

@Value
public class OrderResponse {
	private Long orderId;
	private Instant createdAt;
	private int productId;

	@Tolerate
	public OrderResponse(Order order) {
		orderId = order.getId();
		createdAt = order.getCreatedAt();
		productId = order.getProductId();
	}
}
