package dev.tarasshablii.opora.monolith.apigateway.endpoint.rest;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.api.SponsorsApi;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.mapper.SponsorsDtoMapper;
import dev.tarasshablii.opora.monolith.apigateway.provider.SponsorsProvider;
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

    private final SponsorsProvider sponsorsProvider;
    private final SponsorsDtoMapper mapper;

    @Override
    public ResponseEntity<SponsorResponseDto> createSponsor(SponsorRequestDto sponsorRequestDto) {
        log.debug("Creating new sponsor: {}", sponsorRequestDto.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Optional.of(sponsorRequestDto)
                        .map(mapper::toDto)
                        .map(sponsorsProvider::createNew)
                        .map(mapper::toResponseDto)
                        .orElseThrow());
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
        return ResponseEntity.ok(mapper.toResponseDto(sponsorsProvider.getById(id)));
    }

    @Override
    public ResponseEntity<List<SponsorResponseDto>> getSponsors() {
        log.debug("Fetching all sponsors");
        return ResponseEntity.ok(mapper.toResponseDtoList(sponsorsProvider.getAll()));
    }

    @Override
    public ResponseEntity<SponsorResponseDto> updateSponsor(UUID id, SponsorRequestDto sponsorRequestDto) {
        log.debug("Updating sponsor for id [{}]", id);
        return ResponseEntity.ok(Optional.of(sponsorRequestDto)
                .map(mapper::toDto)
                .map(upd -> sponsorsProvider.updateById(id, upd))
                .map(mapper::toResponseDto)
                .orElseThrow());
    }
}
