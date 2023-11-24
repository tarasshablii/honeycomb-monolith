package dev.tarasshablii.monolith.opora.apigateway.provider;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.MediaDto;
import dev.tarasshablii.monolith.opora.media.endpoint.MediaFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaProvider {

	private final MediaFacade mediaFacade;

	public UUID create(MediaDto mediaDto) {
		return mediaFacade.createNew(mediaDto);
	}

	public void deleteById(UUID mediaId) {
		mediaFacade.deleteById(mediaId);
	}

	public MediaDto getById(UUID mediaId) {
		return mediaFacade.getById(mediaId);
	}

	public void update(MediaDto mediaDto) {
		mediaFacade.update(mediaDto);
	}
}