package dev.tarasshablii.opora.monolith.sponsors.endpoint;

import dev.tarasshablii.opora.monolith.sponsors.domain.service.SponsorService;
import dev.tarasshablii.opora.monolith.sponsors.endpoint.dto.SponsorDto;
import dev.tarasshablii.opora.monolith.sponsors.endpoint.mapper.SponsorDtoModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SponsorsFacade {

    private final SponsorService service;
    private final SponsorDtoModelMapper mapper;

    public SponsorDto createNew(SponsorDto sponsor) {
        return Optional.of(sponsor)
                .map(mapper::toModel)
                .map(service::create)
                .map(mapper::toDto)
                .orElseThrow();
    }

    public void deleteById(UUID sponsorId) {
        service.deleteById(sponsorId);
    }

    public SponsorDto getById(UUID sponsorId) {
        return mapper.toDto(service.getById(sponsorId));
    }

    public List<SponsorDto> getAll() {
        return mapper.toDtoList(service.getAll());
    }

    public SponsorDto updateById(UUID sponsorId, SponsorDto update) {
        return Optional.of(update)
                .map(mapper::toModel)
                .map(upd -> service.updateById(sponsorId, upd))
                .map(mapper::toDto)
                .orElseThrow();
    }
}
