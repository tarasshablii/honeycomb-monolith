package dev.tarasshablii.monolith.opora.initiatives.domain.port;

import dev.tarasshablii.monolith.opora.initiatives.domain.model.Initiative;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InitiativeProvider {
	List<Initiative> findAll();

	Initiative save(Initiative initiative);

	Optional<Initiative> findById(UUID initiativeId);

	boolean existsById(UUID initiativeId);

	void deleteById(UUID initiativeId);

	List<Initiative> findAllBySponsor(UUID sponsorId);
}