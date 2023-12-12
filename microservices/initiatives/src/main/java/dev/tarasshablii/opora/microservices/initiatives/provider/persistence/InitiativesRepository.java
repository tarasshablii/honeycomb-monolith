package dev.tarasshablii.opora.microservices.initiatives.provider.persistence;

import dev.tarasshablii.opora.microservices.initiatives.provider.persistence.entity.InitiativeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface InitiativesRepository extends MongoRepository<InitiativeEntity, UUID> {

    List<InitiativeEntity> findBySponsorId(UUID sponsorId);
}
