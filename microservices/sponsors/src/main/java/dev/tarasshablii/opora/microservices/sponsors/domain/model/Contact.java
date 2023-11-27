package dev.tarasshablii.opora.microservices.sponsors.domain.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Contact {
	private UUID id;
	private String firstName;
	private String lastName;
	private List<Phone> phones;
	private String email;
	private List<Link> links;
}