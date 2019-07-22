package io.github.cepr0.demo.commons.model.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class UuidEntity extends BaseEntity<UUID> {

	@Id
	@GeneratedValue
	private UUID id;

	@Version
	private Integer version;
}
