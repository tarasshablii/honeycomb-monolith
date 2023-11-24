package dev.tarasshablii.opora.monolith.media.provider.persistence.metadata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "metadata")
public class MetadataEntity {

	@Id
	private UUID id;
	private String contentType;
}