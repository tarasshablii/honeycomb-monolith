package dev.tarasshablii.monolith.opora.media.provider.persistence.metadata;

import dev.tarasshablii.monolith.opora.media.provider.persistence.metadata.entity.MetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MetadataRepository extends JpaRepository<MetadataEntity, UUID> {
}