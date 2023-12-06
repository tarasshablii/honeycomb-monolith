package dev.tarasshablii.opora.microservices.initiatives.endpoint.rest;

import dev.tarasshablii.opora.microservices.initiatives.domain.service.InitiativesService;
import dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.api.InitiativesApi;
import dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.dto.InitiativeServiceRequestDto;
import dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.dto.InitiativeServiceResponseDto;
import dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.mapper.InitiativesDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InitiativesController implements InitiativesApi {

	private final InitiativesService service;
	private final InitiativesDtoMapper mapper;


	@Override
	public ResponseEntity<InitiativeServiceResponseDto> createInitiative(InitiativeServiceRequestDto initiativeRequestDto) {
		log.debug("Creating new initiative: {}", initiativeRequestDto.getTitle());
		return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(initiativeRequestDto)
																						  .map(mapper::toModel)
																						  .map(service::create)
																						  .map(mapper::toDto)
																						  .orElseThrow());
	}

	@Override
	public ResponseEntity<Void> deleteInitiative(UUID id) {
		log.debug("Deleting initiative with id [{}]", id);
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<InitiativeServiceResponseDto> getInitiative(UUID id) {
		log.debug("Fetching initiative for id [{}]", id);
		return ResponseEntity.ok(mapper.toDto(service.getById(id)));
	}

	@Override
	public ResponseEntity<List<InitiativeServiceResponseDto>> getInitiatives(UUID sponsor) {
		if (isNull(sponsor)) {
			log.debug("Fetching all initiatives");
			return ResponseEntity.ok(mapper.toDtoList(service.getAll()));
		} else {
			log.debug("Fetching all initiatives by sponsor [{}]", sponsor);
			return ResponseEntity.ok(mapper.toDtoList(service.getAllBySponsor(sponsor)));
		}
	}

	@Override
	public ResponseEntity<InitiativeServiceResponseDto> updateInitiative(UUID id,
			InitiativeServiceRequestDto initiativeRequestDto) {
		log.debug("Updating initiative for id [{}]", id);
		return ResponseEntity.ok(Optional.of(initiativeRequestDto)
													.map(mapper::toModel)
													.map(upd -> service.updateById(id, upd))
													.map(mapper::toDto)
													.orElseThrow());
	}
}
