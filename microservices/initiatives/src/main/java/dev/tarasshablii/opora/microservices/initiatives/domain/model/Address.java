package dev.tarasshablii.opora.microservices.initiatives.domain.model;

import lombok.Data;

@Data
public class Address extends Directions {

	private String street;
	private String town;
	private String directions;
}