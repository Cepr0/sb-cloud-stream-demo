package io.github.cepr0.demo.commons.model.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class IntIdEntity extends BaseEntity<Integer> {

	@Id
	private Integer id;

	@Version
	private Integer version;

	public IntIdEntity(Integer id) {
		this.id = id;
	}
}
