package io.github.cepr0.demo.commons.model.product;

import io.github.cepr0.demo.commons.model.base.IntIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
@DynamicInsert
@DynamicUpdate
public class Product extends IntIdEntity {

	private String name;

	@Tolerate
	public Product(int id, String name) {
		super(id);
		this.name = name;
	}
}
