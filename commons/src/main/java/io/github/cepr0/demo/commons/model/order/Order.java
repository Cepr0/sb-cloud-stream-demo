package io.github.cepr0.demo.commons.model.order;

import io.github.cepr0.demo.commons.model.base.UuidEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
@DynamicInsert
@DynamicUpdate
public class Order extends UuidEntity {

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false, length = 9)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(nullable = false)
	private int productId;

	@Tolerate
	private Order(LocalDateTime createdAt, Status status, int productId) {
		this.createdAt = createdAt;
		this.status = status;
		this.productId = productId;
	}

	public static Order of(int productId) {
		return new Order(
				LocalDateTime.now(),
				Status.PENDING,
				productId
		);
	}

	public enum Status {
		PENDING, COMPLETED, FAILED
	}
}
