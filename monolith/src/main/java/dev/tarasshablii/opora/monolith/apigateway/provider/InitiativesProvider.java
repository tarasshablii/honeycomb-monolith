package dev.tarasshablii.opora.monolith.apigateway.provider;

import dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto;
import dev.tarasshablii.opora.monolith.apigateway.provider.mapper.InitiativeDtoMapper;
import dev.tarasshablii.opora.monolith.initiatives.endpoint.InitiativesFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitiativesProvider {

	private final InitiativesFacade initiativesFacade;
	private final InitiativeDtoMapper mapper;

	public InitiativeDto create(InitiativeDto initiativeRequestDto) {
		return Optional.of(initiativeRequestDto)
							.map(mapper::toInboundDto)
							.map(initiativesFacade::crateNew)
							.map(mapper::toOutboundDto)
							.orElseThrow();
	}

	public void deleteById(UUID initiativeId) {
		initiativesFacade.deleteById(initiativeId);
	}

	public InitiativeDto getById(UUID initiativeId) {
		return mapper.toOutboundDto(initiativesFacade.getById(initiativeId));
	}

	public List<InitiativeDto> getAll(UUID sponsorId) {
		return mapper.toOutboundDtoList(initiativesFacade.getAll(sponsorId));
	}

	public InitiativeDto updateById(UUID initiativeId, InitiativeDto update) {
		return Optional.of(update)
							.map(mapper::toInboundDto)
							.map(upd -> initiativesFacade.updateById(initiativeId, upd))
							.map(mapper::toOutboundDto)
							.orElseThrow();
	}
}
