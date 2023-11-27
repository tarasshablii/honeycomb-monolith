package dev.tarasshablii.opora.microservices.sponsors.provider.persistence.entity;

import dev.tarasshablii.opora.microservices.sponsors.domain.model.PlatformType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Embeddable
@Table(name = "links")
public class LinkEmbeddable {

	private String url;
	private PlatformType platform;
}