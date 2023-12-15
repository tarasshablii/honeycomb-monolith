package dev.tarasshablii.opora.monolith.initiatives.endpoint;

import dev.tarasshablii.opora.monolith.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.monolith.initiatives.domain.service.InitiativesService;
import dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto;
import dev.tarasshablii.opora.monolith.initiatives.endpoint.mapper.InitiativesDtoModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class InitiativesFacade {

    private final InitiativesService service;
    private final InitiativesDtoModelMapper mapper;

    public InitiativeDto crateNew(InitiativeDto initiative) {
        return Optional.of(initiative)
                .map(mapper::toModel)
                .map(service::create)
                .map(mapper::toDto)
                .orElseThrow();
    }

    public void deleteById(UUID initiativeId) {
        service.deleteById(initiativeId);
    }

    public InitiativeDto getById(UUID initiativeId) {
        return mapper.toDto(service.getById(initiativeId));
    }

    public List<InitiativeDto> getAll(UUID sponsorId) {
        List<Initiative> initiatives = isNull(sponsorId) ? service.getAll() : service.getAllBySponsor(sponsorId);
        return mapper.toDtoList(initiatives);
    }

    public InitiativeDto updateById(UUID initiativeId, InitiativeDto update) {
        return Optional.of(update)
                .map(mapper::toModel)
                .map(upd -> service.updateById(initiativeId, upd))
                .map(mapper::toDto)
                .orElseThrow();
    }

}
