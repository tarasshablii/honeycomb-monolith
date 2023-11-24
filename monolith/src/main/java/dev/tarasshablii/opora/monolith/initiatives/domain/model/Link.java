package dev.tarasshablii.opora.monolith.initiatives.domain.model;

import lombok.Data;

@Data
public class Link {

	private String url;
	private PlatformType platform;
}