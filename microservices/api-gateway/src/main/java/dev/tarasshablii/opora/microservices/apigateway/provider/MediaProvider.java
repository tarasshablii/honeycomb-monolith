package dev.tarasshablii.opora.microservices.apigateway.provider;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.MediaDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.media.api.MediaApi;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.media.dto.MediaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaProvider {

	private final MediaApi mediaApi;

	public UUID create(MediaDto mediaDto) {

		try {
			return mediaApi.getApiClient()
								.invokeAPI("/v1/media", HttpMethod.POST, Collections.emptyMap(), new LinkedMultiValueMap<>(),
										mediaDto.getMedia(), new HttpHeaders(), new LinkedMultiValueMap<>(),
										new LinkedMultiValueMap<>(), Collections.singletonList(MediaType.APPLICATION_JSON),
										MediaType.parseMediaType(mediaDto.getContentType()), new String[] { "OAuth2" },
										new ParameterizedTypeReference<MediaResponseDto>() {
										}).getBody().getId();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteById(UUID id) {
		mediaApi.deleteMedia(id);
	}

	public MediaDto getById(UUID id) {
		ResponseEntity<Resource> response = mediaApi.getApiClient().invokeAPI("/v1/media/{id}", HttpMethod.GET, Map.of("id", id),
				new LinkedMultiValueMap<>(), null, new HttpHeaders(), new LinkedMultiValueMap<>(), new LinkedMultiValueMap<>(),
				MediaType.parseMediaTypes(List.of("image/png", "image/jpeg", "application/json")), MediaType.APPLICATION_JSON,
				new String[] {}, new ParameterizedTypeReference<Resource>() {
				});

		return MediaDto.builder().id(id).media(response.getBody())
							.contentType(response.getHeaders().getContentType().toString()).build();
	}

	public void update(MediaDto mediaDto) {
		mediaApi.getApiClient()
				  .invokeAPI("/v1/media/{id}", HttpMethod.PUT, Map.of("id", mediaDto.getId()), new LinkedMultiValueMap<>(),
						  mediaDto.getMedia(), new HttpHeaders(), new LinkedMultiValueMap<>(), new LinkedMultiValueMap<>(),
						  Collections.singletonList(MediaType.APPLICATION_JSON), MediaType.parseMediaType(mediaDto.getContentType()),
						  new String[] { "OAuth2" }, new ParameterizedTypeReference<Void>() {
						  });
	}
}