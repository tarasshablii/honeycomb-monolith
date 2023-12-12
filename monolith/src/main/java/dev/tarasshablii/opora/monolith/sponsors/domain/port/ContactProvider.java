package dev.tarasshablii.opora.monolith.sponsors.domain.port;

import dev.tarasshablii.opora.monolith.sponsors.domain.model.Contact;

import java.util.List;

public interface ContactProvider {

    List<Contact> saveAll(List<Contact> contacts);
}
