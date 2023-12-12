package dev.tarasshablii.opora.monolith.media.domain.port;

import dev.tarasshablii.opora.monolith.media.domain.model.Media;

import java.util.Optional;
import java.util.UUID;

public interface MediaProvider {

    void save(Media media);

    void deleteById(UUID mediaId);

    Optional<Media> findById(UUID mediaId);
}
