package dev.tarasshablii.opora.microservices.apigateway.provider;

import dev.tarasshablii.opora.microservices.apigateway.provider.rest.initiatives.api.InitiativesApi;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.initiatives.dto.InitiativeServiceRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.initiatives.dto.InitiativeServiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitiativesProvider {

	private final InitiativesApi initiativesApi;

	public InitiativeServiceResponseDto create(InitiativeServiceRequestDto initiativeRequestDto) {
		return initiativesApi.createInitiative(initiativeRequestDto).block();
	}

	public void deleteById(UUID initiativeId) {
		initiativesApi.deleteInitiative(initiativeId).block();
	}

	public InitiativeServiceResponseDto getById(UUID initiativeId) {
		return initiativesApi.getInitiative(initiativeId).block();
	}

	public List<InitiativeServiceResponseDto> getAll(UUID sponsor) {
		return initiativesApi.getInitiatives(sponsor).collectList().block();
	}

	public InitiativeServiceResponseDto updateById(UUID initiativeId, InitiativeServiceRequestDto update) {
		return initiativesApi.updateInitiative(initiativeId, update).block();
	}
}
