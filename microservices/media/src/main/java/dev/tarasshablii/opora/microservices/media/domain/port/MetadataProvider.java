package dev.tarasshablii.opora.microservices.media.domain.port;

import dev.tarasshablii.opora.microservices.media.domain.model.Metadata;

import java.util.Optional;
import java.util.UUID;

public interface MetadataProvider {
	Metadata save(Metadata metadata);

	boolean existsById(UUID mediaId);

	void deleteById(UUID mediaId);

	Optional<Metadata> findById(UUID mediaId);
}