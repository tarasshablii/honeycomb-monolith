package dev.tarasshablii.monolith.opora.apigateway.endpoint.rest;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.api.InitiativesApi;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.InitiativeRequestDto;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class InitiativesController implements InitiativesApi {
	@Override
	public ResponseEntity<InitiativeResponseDto> createInitiative(InitiativeRequestDto initiativeRequestDto) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<Void> deleteInitiative(UUID id) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<InitiativeResponseDto> getInitiative(UUID id) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<List<InitiativeResponseDto>> getInitiatives(UUID sponsor) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@Override
	public ResponseEntity<InitiativeResponseDto> updateInitiative(UUID id, InitiativeRequestDto initiativeRequestDto) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}