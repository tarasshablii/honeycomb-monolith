package dev.tarasshablii.opora.monolith.apigateway.provider;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeRequestDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import dev.tarasshablii.opora.monolith.initiatives.endpoint.InitiativesFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitiativesProvider {

	private final InitiativesFacade initiativesFacade;

	public InitiativeResponseDto create(InitiativeRequestDto initiativeRequestDto) {
		return initiativesFacade.crateNew(initiativeRequestDto);
	}

	public void deleteById(UUID initiativeId) {
		initiativesFacade.deleteById(initiativeId);
	}

	public InitiativeResponseDto getById(UUID initiativeId) {
		return initiativesFacade.getById(initiativeId);
	}

	public List<InitiativeResponseDto> getAll(UUID sponsorId) {
		return initiativesFacade.getAll(sponsorId);
	}

	public InitiativeResponseDto updateById(UUID initiativeId, InitiativeRequestDto update) {
		return initiativesFacade.updateById(initiativeId, update);
	}
}