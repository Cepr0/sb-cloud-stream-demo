package io.github.cepr0.demo.commons.event;

import io.github.cepr0.demo.commons.model.order.Reason;
import lombok.Value;

import java.time.LocalDateTime;

import static io.github.cepr0.demo.commons.model.order.Reason.PRODUCT_ENDED;
import static io.github.cepr0.demo.commons.model.order.Reason.PRODUCT_NOT_FOUND;

@Value
public class OrderFailed implements Event {
	private long orderId;
	private LocalDateTime failedAt;
	private int productId;
	private Reason reason;

	public static OrderFailed of(long orderId, int productId, Reason reason) {
		return new OrderFailed(orderId, LocalDateTime.now(), productId, reason);
	}

	public static OrderFailed productNotFound(long orderId, int productId) {
		return new OrderFailed(orderId, LocalDateTime.now(), productId, PRODUCT_NOT_FOUND);
	}

	public static OrderFailed productEnded(long orderId, int productId) {
		return new OrderFailed(orderId, LocalDateTime.now(), productId, PRODUCT_ENDED);
	}

}
