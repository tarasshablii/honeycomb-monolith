package dev.tarasshablii.opora.monolith.sponsors.domain.port;

import dev.tarasshablii.opora.monolith.sponsors.domain.model.Sponsor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SponsorProvider {
	Sponsor save(Sponsor sponsor);

	void deleteById(UUID sponsorId);

	Optional<Sponsor> findById(UUID sponsorId);

	List<Sponsor> findAll();

	boolean existsById(UUID sponsorId);
}