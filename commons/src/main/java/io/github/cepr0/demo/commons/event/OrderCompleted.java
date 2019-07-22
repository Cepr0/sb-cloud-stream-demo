package io.github.cepr0.demo.commons.event;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class OrderCompleted implements Event {
	private UUID orderId;
	private LocalDateTime completedAt;
	private int balance;

	public static OrderCompleted of(UUID orderId, int balance) {
		return new OrderCompleted(orderId, LocalDateTime.now(), balance);
	}
}
