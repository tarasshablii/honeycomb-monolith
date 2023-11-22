package dev.tarasshablii.monolith.opora.sponsors.endpoint;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.monolith.opora.sponsors.domain.service.SponsorService;
import dev.tarasshablii.monolith.opora.sponsors.endpoint.mapper.SponsorDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SponsorsFacade {

	private final SponsorService service;
	private final SponsorDtoMapper mapper;

	public SponsorResponseDto createNew(SponsorRequestDto sponsor) {
		return Optional.of(sponsor)
							.map(mapper::toModel)
							.map(service::create)
							.map(mapper::toDto)
							.orElseThrow();
	}

	public void deleteById(UUID sponsorId) {
		service.deleteById(sponsorId);
	}

	public SponsorResponseDto getById(UUID sponsorId) {
		return mapper.toDto(service.getById(sponsorId));
	}

	public List<SponsorResponseDto> getAll() {
		return mapper.toDtoList(service.getAll());
	}

	public SponsorResponseDto updateById(UUID sponsorId, SponsorRequestDto update) {
		return Optional.of(update)
							.map(mapper::toModel)
							.map(upd -> service.updateById(sponsorId, upd))
							.map(mapper::toDto)
							.orElseThrow();
	}
}