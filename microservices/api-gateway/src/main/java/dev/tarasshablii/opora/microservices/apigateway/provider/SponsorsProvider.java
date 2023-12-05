package dev.tarasshablii.opora.microservices.apigateway.provider;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.mapper.SponsorsMapper;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.api.SponsorsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SponsorsProvider {

	private final SponsorsApi sponsorsApi;
	private final SponsorsMapper mapper;

	public SponsorResponseDto createNew(SponsorRequestDto sponsorRequestDto) {
		return Optional.of(sponsorRequestDto)
							.map(mapper::toServiceRequestDto)
							.map(sponsorsApi::createSponsor)
							.map(mapper::toGatewayResponseDto)
							.orElseThrow();
	}

	public void deleteById(UUID sponsorId) {
		sponsorsApi.deleteSponsor(sponsorId);
	}

	public SponsorResponseDto getById(UUID sponsorId) {
		return Optional.of(sponsorId)
							.map(sponsorsApi::getSponsor)
							.map(mapper::toGatewayResponseDto)
							.orElseThrow();
	}

	public List<SponsorResponseDto> getAll() {
		return mapper.toGatewayResponseList(sponsorsApi.getSponsors());
	}

	public SponsorResponseDto updateById(UUID sponsorId, SponsorRequestDto update) {
		return Optional.of(update)
							.map(mapper::toServiceRequestDto)
							.map(upd -> sponsorsApi.updateSponsor(sponsorId, upd))
							.map(mapper::toGatewayResponseDto)
							.orElseThrow();
	}
}