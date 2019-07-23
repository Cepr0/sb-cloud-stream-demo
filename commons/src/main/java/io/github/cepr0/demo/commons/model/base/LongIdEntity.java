package io.github.cepr0.demo.commons.model.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class LongIdEntity extends BaseEntity<Long> {

	@Id
	@GeneratedValue(generator = "global_sequence")
	@SequenceGenerator(name = "global_sequence")
	private Long id;

	@Version
	private Integer version;
}
