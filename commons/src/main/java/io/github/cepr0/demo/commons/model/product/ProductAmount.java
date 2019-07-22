package io.github.cepr0.demo.commons.model.product;

import io.github.cepr0.demo.commons.model.base.IntIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_amounts")
@DynamicInsert
@DynamicUpdate
public class ProductAmount extends IntIdEntity {

	@MapsId
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private Product product;

	@Column(nullable = false)
	private int amount;
}
