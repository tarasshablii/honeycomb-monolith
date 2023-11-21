package dev.tarasshablii.monolith.opora.apigateway.endpoint.rest;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.api.MediaApi;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.MediaResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MediaController implements MediaApi {
	@Override
	public ResponseEntity<MediaResponseDto> createMedia(Resource body) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<Void> deleteMedia(UUID id) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<Resource> getMedia(UUID id) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<Void> updateMedia(UUID id, Resource body) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}