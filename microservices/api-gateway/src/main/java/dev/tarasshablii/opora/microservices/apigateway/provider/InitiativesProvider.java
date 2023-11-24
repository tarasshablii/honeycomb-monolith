package dev.tarasshablii.opora.microservices.apigateway.provider;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.InitiativeRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.mapper.InitiativesMapper;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.api.initiatives.InitiativesApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitiativesProvider {

	private final InitiativesApi initiativesApi;
	private final InitiativesMapper mapper;

	public InitiativeResponseDto create(InitiativeRequestDto initiativeRequestDto) {
		return Optional.of(initiativeRequestDto)
							.map(mapper::toServiceRequestDto)
							.map(initiativesApi::createInitiative)
							.map(mapper::toGatewayResponseDto)
							.orElseThrow();
	}

	public void deleteById(UUID initiativeId) {
		initiativesApi.deleteInitiative(initiativeId);
	}

	public InitiativeResponseDto getById(UUID initiativeId) {
		return mapper.toGatewayResponseDto(initiativesApi.getInitiative(initiativeId));
	}

	public List<InitiativeResponseDto> getAll(UUID sponsor) {
		return mapper.toGatewayResponseList(initiativesApi.getInitiatives(sponsor));
	}

	public InitiativeResponseDto updateById(UUID initiativeId, InitiativeRequestDto update) {
		return Optional.of(update)
							.map(mapper::toServiceRequestDto)
							.map(upd -> initiativesApi.updateInitiative(initiativeId, upd))
							.map(mapper::toGatewayResponseDto)
							.orElseThrow();
	}
}