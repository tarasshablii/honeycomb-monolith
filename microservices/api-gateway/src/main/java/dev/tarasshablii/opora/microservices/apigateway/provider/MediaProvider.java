package dev.tarasshablii.opora.microservices.apigateway.provider;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.MediaDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.api.media.MediaApi;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.media.MediaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.io.File;
import java.io.IOException;
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
										mediaDto.getMedia().getFile(), new HttpHeaders(), new LinkedMultiValueMap<>(),
										new LinkedMultiValueMap<>(), Collections.singletonList(MediaType.APPLICATION_JSON),
										MediaType.parseMediaType(mediaDto.getContentType()), new String[] { "OAuth2" },
										new ParameterizedTypeReference<MediaResponseDto>() {
										}).getBody().getId();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteById(UUID id) {
		mediaApi.deleteMedia(id);
	}

	public MediaDto getById(UUID id) {
		ResponseEntity<File> response = mediaApi.getApiClient().invokeAPI("/v1/media/{id}", HttpMethod.GET, Map.of("id", id),
				new LinkedMultiValueMap<>(), null, new HttpHeaders(), new LinkedMultiValueMap<>(), new LinkedMultiValueMap<>(),
				MediaType.parseMediaTypes(List.of("image/png", "image/jpeg", "application/json")), MediaType.APPLICATION_JSON,
				new String[] {}, new ParameterizedTypeReference<File>() {
				});

		return MediaDto.builder().id(id).media(new FileSystemResource(response.getBody()))
							.contentType(response.getHeaders().getContentType().toString()).build();
	}

	public void update(MediaDto mediaDto) {
		mediaApi.getApiClient()
				  .invokeAPI("/v1/media/{id}", HttpMethod.GET, Map.of("id", mediaDto.getId()), new LinkedMultiValueMap<>(), null,
						  new HttpHeaders(), new LinkedMultiValueMap<>(), new LinkedMultiValueMap<>(),
						  Collections.singletonList(MediaType.APPLICATION_JSON), MediaType.parseMediaType(mediaDto.getContentType()),
						  new String[] { "OAuth2" }, new ParameterizedTypeReference<Void>() {
						  });
	}
}