package io.github.cepr0.demo.commons.event;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OrderCompleted implements Event {
	private long orderId;
	private LocalDateTime completedAt;
	private int balance;

	public static OrderCompleted of(long orderId, int balance) {
		return new OrderCompleted(orderId, LocalDateTime.now(), balance);
	}
}
