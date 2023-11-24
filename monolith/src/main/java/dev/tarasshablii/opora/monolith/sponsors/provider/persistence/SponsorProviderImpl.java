package dev.tarasshablii.opora.monolith.sponsors.provider.persistence;

import dev.tarasshablii.opora.monolith.sponsors.domain.model.Sponsor;
import dev.tarasshablii.opora.monolith.sponsors.domain.port.SponsorProvider;
import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.mapper.SponsorEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SponsorProviderImpl implements SponsorProvider {

	private final SponsorRepository repository;
	private final SponsorEntityMapper mapper;

	@Override
	public Sponsor save(Sponsor sponsor) {
		return Optional.of(sponsor)
							.map(mapper::toEntity)
							.map(repository::save)
							.map(mapper::toModel)
							.orElseThrow();
	}

	@Override
	public void deleteById(UUID sponsorId) {
		repository.deleteById(sponsorId);
	}

	@Override
	public Optional<Sponsor> findById(UUID sponsorId) {
		return repository.findById(sponsorId)
							  .map(mapper::toModel);
	}

	@Override
	public List<Sponsor> findAll() {
		return mapper.toModelList(repository.findAll());
	}

	@Override
	public boolean existsById(UUID sponsorId) {
		return repository.existsById(sponsorId);
	}
}