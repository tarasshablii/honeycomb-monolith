package dev.tarasshablii.opora.monolith.sponsors.domain.model;

import lombok.Data;

@Data
public class Link {
	private String url;
	private PlatformType platform;
}