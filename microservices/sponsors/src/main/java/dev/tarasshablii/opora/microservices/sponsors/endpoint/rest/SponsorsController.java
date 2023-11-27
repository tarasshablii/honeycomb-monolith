package dev.tarasshablii.opora.microservices.sponsors.endpoint.rest;

import dev.tarasshablii.opora.microservices.sponsors.domain.service.SponsorService;
import dev.tarasshablii.opora.microservices.sponsors.endpoint.rest.api.SponsorsApi;
import dev.tarasshablii.opora.microservices.sponsors.endpoint.rest.dto.SponsorServiceRequestDto;
import dev.tarasshablii.opora.microservices.sponsors.endpoint.rest.dto.SponsorServiceResponseDto;
import dev.tarasshablii.opora.microservices.sponsors.endpoint.rest.mapper.SponsorDtoMapper;
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
public class SponsorsController implements SponsorsApi {

	private final SponsorService service;
	private final SponsorDtoMapper mapper;

	@Override
	public ResponseEntity<SponsorServiceResponseDto> createSponsor(SponsorServiceRequestDto sponsorRequestDto) {
		log.debug("Creating new sponsor: {}", sponsorRequestDto.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(sponsorRequestDto)
																						  .map(mapper::toModel)
																						  .map(service::create)
																						  .map(mapper::toDto)
																						  .orElseThrow());
	}

	@Override
	public ResponseEntity<Void> deleteSponsor(UUID id) {
		log.debug("Deleting sponsor with id [{}]", id);
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<SponsorServiceResponseDto> getSponsor(UUID id) {
		log.debug("Fetching sponsor for id [{}]", id);
		return ResponseEntity.ok(mapper.toDto(service.getById(id)));
	}

	@Override
	public ResponseEntity<List<SponsorServiceResponseDto>> getSponsors() {
		log.debug("Fetching all sponsors");
		return ResponseEntity.ok(mapper.toDtoList(service.getAll()));
	}

	@Override
	public ResponseEntity<SponsorServiceResponseDto> updateSponsor(UUID id, SponsorServiceRequestDto sponsorRequestDto) {
		log.debug("Updating sponsor for id [{}]", id);
		return ResponseEntity.ok(Optional.of(sponsorRequestDto)
													.map(mapper::toModel)
													.map(upd -> service.updateById(id, upd))
													.map(mapper::toDto)
													.orElseThrow());
	}
}