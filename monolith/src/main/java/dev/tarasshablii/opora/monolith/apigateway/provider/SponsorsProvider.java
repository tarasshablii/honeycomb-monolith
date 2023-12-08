package dev.tarasshablii.opora.monolith.apigateway.provider;

import dev.tarasshablii.opora.monolith.apigateway.provider.dto.SponsorDto;
import dev.tarasshablii.opora.monolith.apigateway.provider.mapper.SponsorDtoMapper;
import dev.tarasshablii.opora.monolith.sponsors.endpoint.SponsorsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SponsorsProvider {

	private final SponsorsFacade sponsorsFacade;
	private final SponsorDtoMapper mapper;

	public SponsorDto createNew(SponsorDto sponsorDto) {
		return Optional.of(sponsorDto)
							.map(mapper::toInboundDto)
							.map(sponsorsFacade::createNew)
							.map(mapper::toOutboundDto)
							.orElseThrow();
	}

	public void deleteById(UUID sponsorId) {
		sponsorsFacade.deleteById(sponsorId);
	}

	public SponsorDto getById(UUID sponsorId) {
		return mapper.toOutboundDto(sponsorsFacade.getById(sponsorId));
	}

	public List<SponsorDto> getAll() {
		return mapper.toOutboundDtoList(sponsorsFacade.getAll());
	}

	public SponsorDto updateById(UUID sponsorId, SponsorDto update) {
		return Optional.of(update)
							.map(mapper::toInboundDto)
							.map(upd -> sponsorsFacade.updateById(sponsorId, upd))
							.map(mapper::toOutboundDto)
							.orElseThrow();
	}
}
