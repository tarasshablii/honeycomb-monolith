package dev.tarasshablii.opora.microservices.initiatives.provider.persistence.entity;

import dev.tarasshablii.opora.microservices.initiatives.domain.model.Contact;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@TypeAlias("novaposhta")
public class NovaPoshtaEntity extends DirectionsEntity {

	private String town;
	private Integer office;
	private Contact recipient;
	private String directions;
}
