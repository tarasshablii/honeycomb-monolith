package dev.tarasshablii.opora.monolith.initiatives.domain.service;

import dev.tarasshablii.opora.monolith.common.exception.NotFoundException;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.monolith.initiatives.domain.port.InitiativeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitiativesService {

	private static final String NOT_FOUND_MESSAGE = "Initiative with id [%s] not found";
	private final InitiativeProvider provider;

	public List<Initiative> getAll() {
		return provider.findAll();
	}

	public Initiative create(Initiative initiative) {
		return provider.save(initiative);
	}

	public Initiative getById(UUID initiativeId) {
		return provider.findById(initiativeId)
							.orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(initiativeId)));
	}

	public Initiative updateById(UUID initiativeId, Initiative update) {
		if (!provider.existsById(initiativeId)) {
			throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(initiativeId));
		}
		update.setId(initiativeId);
		return provider.save(update);
	}

	public void deleteById(UUID initiativeId) {
		if (!provider.existsById(initiativeId)) {
			throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(initiativeId));
		}
		provider.deleteById(initiativeId);
	}

	public List<Initiative> getAllBySponsor(UUID sponsorId) {
		return provider.findAllBySponsor(sponsorId);
	}
}