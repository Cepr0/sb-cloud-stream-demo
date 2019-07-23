package io.github.cepr0.demo.commons.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "product_orders")
@Immutable
public class ProductOrder implements Persistable<Long> {

	@Id
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Product product;

	@Tolerate
	public ProductOrder(long orderId, Product product) {
		this.id = orderId;
		this.product = product;
	}

	@Override
	public boolean isNew() {
		return true;
	}
}
