package dev.tarasshablii.opora.microservices.apigateway.endpoint.rest;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.api.SponsorsApi;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.SponsorsProvider;
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

	private final SponsorsProvider sponsorsProvider;

	@Override
	public ResponseEntity<SponsorResponseDto> createSponsor(SponsorRequestDto sponsorRequestDto) {
		log.debug("Creating new sponsor: {}", sponsorRequestDto.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(sponsorsProvider.createNew(sponsorRequestDto));
	}

	@Override
	public ResponseEntity<Void> deleteSponsor(UUID id) {
		log.debug("Deleting sponsor with id [{}]", id);
		sponsorsProvider.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<SponsorResponseDto> getSponsor(UUID id) {
		log.debug("Fetching sponsor for id [{}]", id);
		return ResponseEntity.ok(sponsorsProvider.getById(id));
	}

	@Override
	public ResponseEntity<List<SponsorResponseDto>> getSponsors() {
		log.debug("Fetching all sponsors");
		return ResponseEntity.ok(sponsorsProvider.getAll());
	}

	@Override
	public ResponseEntity<SponsorResponseDto> updateSponsor(UUID id, SponsorRequestDto sponsorRequestDto) {
		log.debug("Updating sponsor for id [{}]", id);
		return ResponseEntity.ok(sponsorsProvider.updateById(id, sponsorRequestDto));
	}
}