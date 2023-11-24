package dev.tarasshablii.opora.monolith.media.endpoint;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.MediaDto;
import dev.tarasshablii.opora.monolith.media.domain.service.MediaService;
import dev.tarasshablii.opora.monolith.media.endpoint.mapper.MediaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaFacade {

	private final MediaService service;
	private final MediaDtoMapper mapper;

	public UUID createNew(MediaDto mediaDto) {
		return service.create(mapper.toModel(mediaDto));
	}

	public void deleteById(UUID mediaId) {
		service.deleteById(mediaId);
	}

	public MediaDto getById(UUID mediaId) {
		return mapper.toDto(service.getById(mediaId));
	}

	public void update(MediaDto mediaDto) {
		service.update(mapper.toModel(mediaDto));
	}
}