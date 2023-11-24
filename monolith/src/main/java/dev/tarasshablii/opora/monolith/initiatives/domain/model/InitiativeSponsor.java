package dev.tarasshablii.opora.monolith.initiatives.domain.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Data
public class InitiativeSponsor {

	@Indexed
	private UUID id;
	private String userName;
	private String name;
}