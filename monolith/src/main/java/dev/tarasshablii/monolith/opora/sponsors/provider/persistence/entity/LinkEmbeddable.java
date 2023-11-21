package dev.tarasshablii.monolith.opora.sponsors.provider.persistence.entity;

import dev.tarasshablii.monolith.opora.sponsors.domain.model.PlatformType;
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