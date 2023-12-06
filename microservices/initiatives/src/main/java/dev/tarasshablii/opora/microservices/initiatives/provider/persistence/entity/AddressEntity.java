package dev.tarasshablii.opora.microservices.initiatives.provider.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;

@Data
@TypeAlias("address")
public class AddressEntity extends DirectionsEntity {

	private String street;
	private String town;
	private String directions;
}
