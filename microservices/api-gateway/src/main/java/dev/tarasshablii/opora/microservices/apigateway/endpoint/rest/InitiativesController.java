package dev.tarasshablii.opora.microservices.apigateway.endpoint.rest;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.api.InitiativesApi;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.InitiativeRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.mapper.InitiativesMapper;
import dev.tarasshablii.opora.microservices.apigateway.provider.InitiativesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InitiativesController implements InitiativesApi {

	private final InitiativesProvider initiativesProvider;
	private final InitiativesMapper mapper;

	@Override
	public ResponseEntity<InitiativeResponseDto> createInitiative(InitiativeRequestDto initiativeRequestDto) {
		log.debug("Creating new initiative: {}", initiativeRequestDto.getTitle());
		return ResponseEntity.status(HttpStatus.CREATED)
									.body(Optional.of(initiativeRequestDto)
													  .map(mapper::toServiceRequestDto)
													  .map(initiativesProvider::create)
													  .map(mapper::toGatewayResponseDto)
													  .orElseThrow());
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
		return ResponseEntity.ok(mapper.toGatewayResponseDto(initiativesProvider.getById(id)));
	}

	@Override
	public ResponseEntity<List<InitiativeResponseDto>> getInitiatives(UUID sponsor) {
		log.debug("Fetching all initiatives");
		return ResponseEntity.ok(mapper.toGatewayResponseList(initiativesProvider.getAll(sponsor)));
	}

	@Override
	public ResponseEntity<InitiativeResponseDto> updateInitiative(UUID id, InitiativeRequestDto initiativeRequestDto) {
		log.debug("Updating initiative for id [{}]", id);
		return ResponseEntity.ok(Optional.of(initiativeRequestDto)
													.map(mapper::toServiceRequestDto)
													.map(upd -> initiativesProvider.updateById(id, upd))
													.map(mapper::toGatewayResponseDto)
													.orElseThrow());
	}
}
