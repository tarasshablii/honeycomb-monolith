package dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity;

import dev.tarasshablii.opora.monolith.sponsors.domain.model.PhoneType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Data
@Embeddable
@Table(name = "phones")
public class PhoneEmbeddable {

	private String number;
	private Set<PhoneType> types;
}