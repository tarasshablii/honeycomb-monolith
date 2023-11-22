package dev.tarasshablii.monolith.opora.apigateway.endpoint.rest;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.api.InitiativesApi;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.InitiativeRequestDto;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import dev.tarasshablii.monolith.opora.apigateway.provider.InitiativesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InitiativesController implements InitiativesApi {

	private final InitiativesProvider initiativesProvider;

	@Override
	public ResponseEntity<InitiativeResponseDto> createInitiative(InitiativeRequestDto initiativeRequestDto) {
		log.debug("Creating new initiative: {}", initiativeRequestDto.getTitle());
		return ResponseEntity.status(HttpStatus.CREATED).body(initiativesProvider.create(initiativeRequestDto));
	}

	@Override
	public ResponseEntity<Void> deleteInitiative(UUID id) {
		log.debug("Deleting initiative with id [{}]", id);
		initiativesProvider.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<InitiativeResponseDto> getInitiative(UUID id) {
		log.debug("Fetching initiative for id [{}]", id);
		return ResponseEntity.ok(initiativesProvider.getById(id));
	}

	@Override
	public ResponseEntity<List<InitiativeResponseDto>> getInitiatives(UUID sponsor) {
		log.debug("Fetching all initiatives");
		return ResponseEntity.ok(initiativesProvider.getAll(sponsor));
	}

	@Override
	public ResponseEntity<InitiativeResponseDto> updateInitiative(UUID id, InitiativeRequestDto initiativeRequestDto) {
		log.debug("Updating initiative for id [{}]", id);
		return ResponseEntity.ok(initiativesProvider.updateById(id, initiativeRequestDto));
	}
}