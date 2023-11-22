package dev.tarasshablii.monolith.opora.initiatives.provider.persistence;

import dev.tarasshablii.monolith.opora.initiatives.domain.model.Initiative;
import dev.tarasshablii.monolith.opora.initiatives.domain.port.InitiativeProvider;
import dev.tarasshablii.monolith.opora.initiatives.provider.persistence.mapper.InitiativeEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class InitiativeProviderImpl implements InitiativeProvider {

	private final InitiativesRepository repository;
	private final InitiativeEntityMapper mapper;

	@Override
	public List<Initiative> findAll() {
		return mapper.toModelList(repository.findAll());
	}

	@Override
	public Initiative save(Initiative initiative) {
		if (isNull(initiative.getId())) {
			initiative.setId(UUID.randomUUID());
		}
		return Optional.of(initiative)
							.map(mapper::toEntity)
							.map(repository::save)
							.map(mapper::toModel)
							.orElseThrow();
	}

	@Override
	public Optional<Initiative> findById(UUID initiativeId) {
		return repository.findById(initiativeId)
							  .map(mapper::toModel);
	}

	@Override
	public boolean existsById(UUID initiativeId) {
		return repository.existsById(initiativeId);
	}

	@Override
	public List<Initiative> findAllBySponsor(UUID sponsorId) {
		return mapper.toModelList(repository.findBySponsorId(sponsorId));
	}

	@Override
	public void deleteById(UUID initiativeId) {
		repository.deleteById(initiativeId);
	}
}