package dev.tarasshablii.opora.monolith.sponsors.provider.persistence;

import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity.SponsorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SponsorRepository extends JpaRepository<SponsorEntity, UUID> {
}