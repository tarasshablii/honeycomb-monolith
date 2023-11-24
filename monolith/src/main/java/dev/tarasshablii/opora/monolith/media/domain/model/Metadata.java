package dev.tarasshablii.opora.monolith.media.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Metadata {
	private UUID id;
	private String contentType;
}