package dev.tarasshablii.opora.monolith.media.provider.persistence.metadata;

import dev.tarasshablii.opora.monolith.media.domain.model.Metadata;
import dev.tarasshablii.opora.monolith.media.domain.port.MetadataProvider;
import dev.tarasshablii.opora.monolith.media.provider.persistence.metadata.mapper.MetadataEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MetadataProviderImpl implements MetadataProvider {

    private final MetadataRepository repository;
    private final MetadataEntityMapper mapper;

    @Override
    public Metadata save(Metadata metadata) {
        return Optional.of(metadata)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElseThrow();
    }

    @Override
    public boolean existsById(UUID mediaId) {
        return repository.existsById(mediaId);
    }

    @Override
    public void deleteById(UUID mediaId) {
        repository.deleteById(mediaId);
    }

    @Override
    public Optional<Metadata> findById(UUID mediaId) {
        return repository.findById(mediaId)
                .map(mapper::toModel);
    }
}
