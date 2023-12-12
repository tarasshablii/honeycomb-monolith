package dev.tarasshablii.opora.microservices.sponsors.provider.persistence;

import dev.tarasshablii.opora.microservices.sponsors.domain.model.Contact;
import dev.tarasshablii.opora.microservices.sponsors.domain.port.ContactProvider;
import dev.tarasshablii.opora.microservices.sponsors.provider.persistence.mapper.ContactEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactProviderImpl implements ContactProvider {

    private final ContactRepository repository;
    private final ContactEntityMapper mapper;

    @Override
    public List<Contact> saveAll(List<Contact> contacts) {
        return Optional.of(contacts)
                .map(mapper::toEntityList)
                .map(repository::saveAll)
                .map(mapper::toModelList)
                .orElseThrow();
    }
}
