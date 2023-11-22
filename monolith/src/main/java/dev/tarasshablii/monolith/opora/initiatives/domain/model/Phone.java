package dev.tarasshablii.monolith.opora.initiatives.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Phone {

	private String number;
	private List<PhoneType> types;
}