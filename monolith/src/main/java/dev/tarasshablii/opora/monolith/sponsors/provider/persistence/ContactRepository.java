package dev.tarasshablii.opora.monolith.sponsors.provider.persistence;

import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {
}