package io.github.cepr0.demo.commons.model;

import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Order implements Serializable {

	private UUID id;
	private LocalDateTime createdAt;
	private Status status;
	private int productId;

	@Tolerate
	private Order(UUID id, LocalDateTime createdAt, Status status, int productId) {
		this.id = id;
		this.createdAt = createdAt;
		this.status = status;
		this.productId = productId;
	}

	public static Order of(int productId) {
		return new Order(
				UUID.randomUUID(),
				LocalDateTime.now(),
				Status.PENDING,
				productId
		);
	}

	public enum Status {
		PENDING, COMPLETED, FAILED
	}
}
