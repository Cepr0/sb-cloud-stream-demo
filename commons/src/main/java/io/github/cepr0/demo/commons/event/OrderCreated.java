package io.github.cepr0.demo.commons.event;

import io.github.cepr0.demo.commons.model.order.Order;
import lombok.Value;

@Value
public class OrderCreated implements Event {
	private Order order;
}
