package dev.tarasshablii.opora.monolith.media.domain.service;

import dev.tarasshablii.opora.monolith.common.exception.NotFoundException;
import dev.tarasshablii.opora.monolith.media.domain.model.Media;
import dev.tarasshablii.opora.monolith.media.domain.model.Metadata;
import dev.tarasshablii.opora.monolith.media.domain.port.MediaProvider;
import dev.tarasshablii.opora.monolith.media.domain.port.MetadataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

	private static final String NOT_FOUND_MESSAGE = "Media with id [%s] not found";
	private final MediaProvider mediaProvider;
	private final MetadataProvider metadataProvider;

	public UUID create(Media media) {
		media.generateId();
		mediaProvider.save(media);
		return metadataProvider.save(media.getMetadata()).getId();
	}

	public void deleteById(UUID mediaId) {
		if (!metadataProvider.existsById(mediaId)) {
			throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(mediaId));
		}
		mediaProvider.deleteById(mediaId);
		metadataProvider.deleteById(mediaId);
	}

	public Media getById(UUID mediaId) {
		var metadata = metadataProvider.findById(mediaId)
												 .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(mediaId)));
		var media = mediaProvider.findById(mediaId)
										 .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(mediaId)));
		media.setMetadata(metadata);
		return media;
	}

	public void update(Media media) {
		var mediaId = Optional.of(media)
									 .map(Media::getMetadata)
									 .map(Metadata::getId)
									 .orElseThrow();
		if (!metadataProvider.existsById(mediaId)) {
			throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(mediaId));
		}
		mediaProvider.save(media);
		metadataProvider.save(media.getMetadata());
	}
}