package dev.tarasshablii.opora.microservices.initiatives.provider.persistence.entity;

import dev.tarasshablii.opora.microservices.initiatives.domain.model.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
public class InitiativeEntity {

	@Id
	private UUID id;
	private String title;
	private String description;
	private UUID media;
	private InitiativeStatus status;
	private Boolean isUrgent;
	private InitiativeSponsor sponsor;
	private List<Contact> contacts;
	private List<Directions> directions;
	private List<InitiativeItem> items;
}