package dev.tarasshablii.opora.monolith.initiatives.endpoint;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeRequestDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.monolith.initiatives.domain.service.InitiativesService;
import dev.tarasshablii.opora.monolith.initiatives.endpoint.mapper.InitiativesDtoMapper;
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
	private final InitiativesDtoMapper mapper;

	public InitiativeResponseDto crateNew(InitiativeRequestDto initiative) {
		return Optional.of(initiative)
							.map(mapper::toModel)
							.map(service::create)
							.map(mapper::toDto)
							.orElseThrow();
	}

	public void deleteById(UUID initiativeId) {
		service.deleteById(initiativeId);
	}

	public InitiativeResponseDto getById(UUID initiativeId) {
		return mapper.toDto(service.getById(initiativeId));
	}

	public List<InitiativeResponseDto> getAll(UUID sponsorId) {
		List<Initiative> initiatives = isNull(sponsorId) ? service.getAll() : service.getAllBySponsor(sponsorId);
		return mapper.toDtoList(initiatives);
	}

	public InitiativeResponseDto updateById(UUID initiativeId, InitiativeRequestDto update) {
		return Optional.of(update)
							.map(mapper::toModel)
							.map(upd -> service.updateById(initiativeId, upd))
							.map(mapper::toDto)
							.orElseThrow();
	}

}