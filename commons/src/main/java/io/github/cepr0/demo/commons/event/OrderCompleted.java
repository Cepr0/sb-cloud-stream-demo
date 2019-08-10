package io.github.cepr0.demo.commons.event;

import lombok.Value;

import java.time.Instant;

@Value
public class OrderCompleted implements Event {
	private long orderId;
	private Instant completedAt;
	private int balance;

	public static OrderCompleted of(long orderId, int balance) {
		return new OrderCompleted(orderId, Instant.now(), balance);
	}
}
