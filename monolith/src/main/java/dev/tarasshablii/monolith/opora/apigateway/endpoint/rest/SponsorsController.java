package dev.tarasshablii.monolith.opora.apigateway.endpoint.rest;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.api.SponsorsApi;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.monolith.opora.sponsors.endpoint.SponsorFacade;
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
public class SponsorsController implements SponsorsApi {

	private final SponsorFacade sponsorFacade;

	@Override
	public ResponseEntity<SponsorResponseDto> createSponsor(SponsorRequestDto sponsorRequestDto) {
		log.debug("Creating new sponsor: {}", sponsorRequestDto.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(sponsorFacade.createNew(sponsorRequestDto));
	}

	@Override
	public ResponseEntity<Void> deleteSponsor(UUID id) {
		log.debug("Deleting sponsor with id [{}]", id);
		sponsorFacade.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<SponsorResponseDto> getSponsor(UUID id) {
		log.debug("Fetching sponsor for id [{}]", id);
		return ResponseEntity.ok(sponsorFacade.getById(id));
	}

	@Override
	public ResponseEntity<List<SponsorResponseDto>> getSponsors() {
		log.debug("Fetching all sponsors");
		return ResponseEntity.ok(sponsorFacade.getAll());
	}

	@Override
	public ResponseEntity<SponsorResponseDto> updateSponsor(UUID id, SponsorRequestDto sponsorRequestDto) {
		log.debug("Updating sponsor for id [{}]", id);
		return ResponseEntity.ok(sponsorFacade.updateById(id, sponsorRequestDto));
	}
}