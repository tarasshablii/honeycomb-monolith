package dev.tarasshablii.monolith.opora.media.domain.port;

import dev.tarasshablii.monolith.opora.media.domain.model.Metadata;

import java.util.Optional;
import java.util.UUID;

public interface MetadataProvider {
	Metadata save(Metadata metadata);

	boolean existsById(UUID mediaId);

	void deleteById(UUID mediaId);

	Optional<Metadata> findById(UUID mediaId);
}