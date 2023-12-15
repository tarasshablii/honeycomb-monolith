package dev.tarasshablii.opora.microservices.apigateway.provider;

import dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.api.SponsorsApi;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.dto.SponsorServiceRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.dto.SponsorServiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SponsorsProvider {

    private final SponsorsApi sponsorsApi;

    public SponsorServiceResponseDto createNew(SponsorServiceRequestDto sponsorRequestDto) {
        return sponsorsApi.createSponsor(sponsorRequestDto).block();
    }

    public void deleteById(UUID sponsorId) {
        sponsorsApi.deleteSponsor(sponsorId).block();
    }

    public SponsorServiceResponseDto getById(UUID sponsorId) {
        return sponsorsApi.getSponsor(sponsorId).block();
    }

    public List<SponsorServiceResponseDto> getAll() {
        return sponsorsApi.getSponsors().collectList().block();
    }

    public SponsorServiceResponseDto updateById(UUID sponsorId, SponsorServiceRequestDto update) {
        return sponsorsApi.updateSponsor(sponsorId, update).block();
    }
}
