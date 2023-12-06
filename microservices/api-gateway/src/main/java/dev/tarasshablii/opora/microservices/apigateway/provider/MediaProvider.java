package dev.tarasshablii.opora.microservices.apigateway.provider;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.MediaDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.media.api.MediaApi;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.media.dto.MediaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.parseMediaType;

@Component
@RequiredArgsConstructor
public class MediaProvider {

	private final MediaApi mediaApi;
	private final WebClient mediaWebClient;

	public UUID create(MediaDto mediaDto) {

		return mediaWebClient.post()
									.contentType(parseMediaType(mediaDto.getContentType()))
									.accept(APPLICATION_JSON)
									.bodyValue(mediaDto.getMedia())
									.retrieve()
									.bodyToMono(MediaResponseDto.class)
									.map(MediaResponseDto::getId)
									.block();
	}

	public void deleteById(UUID id) {
		mediaApi.deleteMedia(id).block();
	}

	public MediaDto getById(UUID id) {
		var response = mediaWebClient.get()
											  .uri(uriBuilder -> uriBuilder.path("/%s".formatted(id)).build())
											  .accept(parseMediaType("image/png"), parseMediaType("image/jpeg"),
													  parseMediaType("application/json"))
											  .retrieve()
											  .toEntity(Resource.class)
											  .block();

		return MediaDto.builder()
							.id(id)
							.media(Optional.ofNullable(response)
												.map(ResponseEntity::getBody)
												.orElseThrow())
							.contentType(Optional.of(response)
														.map(ResponseEntity::getHeaders)
														.map(HttpHeaders::getContentType)
														.map(MediaType::toString)
														.orElseThrow())
							.build();
	}

	public void update(MediaDto mediaDto) {
		mediaWebClient.put()
						  .uri(uriBuilder -> uriBuilder.path("/%s".formatted(mediaDto.getId().toString())).build())
						  .contentType(parseMediaType(mediaDto.getContentType()))
						  .accept(APPLICATION_JSON)
						  .bodyValue(mediaDto.getMedia())
						  .retrieve()
						  .toBodilessEntity()
						  .block();
	}
}
