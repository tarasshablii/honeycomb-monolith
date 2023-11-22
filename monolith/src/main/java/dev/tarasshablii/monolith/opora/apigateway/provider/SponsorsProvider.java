package dev.tarasshablii.monolith.opora.apigateway.provider;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.monolith.opora.sponsors.endpoint.SponsorsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SponsorsProvider {

	private final SponsorsFacade sponsorsFacade;


	public SponsorResponseDto createNew(SponsorRequestDto sponsorRequestDto) {
		return sponsorsFacade.createNew(sponsorRequestDto);
	}

	public void deleteById(UUID sponsorId) {
		sponsorsFacade.deleteById(sponsorId);
	}

	public SponsorResponseDto getById(UUID sponsorId) {
		return sponsorsFacade.getById(sponsorId);
	}

	public List<SponsorResponseDto> getAll() {
		return sponsorsFacade.getAll();
	}

	public SponsorResponseDto updateById(UUID sponsorId, SponsorRequestDto update) {
		return sponsorsFacade.updateById(sponsorId, update);
	}
}