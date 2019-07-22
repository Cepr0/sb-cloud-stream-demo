package io.github.cepr0.demo.commons.model.order;

import io.github.cepr0.demo.commons.model.base.UuidEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
@DynamicInsert
@DynamicUpdate
public class Order extends UuidEntity {

	@Column(nullable = false)
	private Instant createdAt;

	@Column(nullable = false, length = 9)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(nullable = false)
	private int productId;

	@Column(length = 17)
	@Enumerated(EnumType.STRING)
	private Reason reason;

	@Tolerate
	private Order(Instant createdAt, Status status, int productId) {
		this.createdAt = createdAt;
		this.status = status;
		this.productId = productId;
	}

	public static Order of(int productId) {
		return new Order(
				Instant.now(),
				Status.PENDING,
				productId
		);
	}

	public Order markAsCompleted() {
		this.status = Status.COMPLETED;
		return this;
	}

	public Order markAsFailed(Reason reason) {
		this.status = Status.FAILED;
		this.reason = reason;
		return this;
	}

	public enum Status {
		PENDING, COMPLETED, FAILED
	}
}
