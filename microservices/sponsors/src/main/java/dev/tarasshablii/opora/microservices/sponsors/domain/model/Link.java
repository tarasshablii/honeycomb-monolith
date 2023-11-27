package dev.tarasshablii.opora.microservices.sponsors.domain.model;

import lombok.Data;

@Data
public class Link {
	private String url;
	private PlatformType platform;
}