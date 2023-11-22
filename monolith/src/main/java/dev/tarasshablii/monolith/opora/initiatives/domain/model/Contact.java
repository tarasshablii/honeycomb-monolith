package dev.tarasshablii.monolith.opora.initiatives.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Contact {

	private String firstName;
	private String lastName;
	private List<Phone> phones;
	private String email;
	private List<Link> links;
}