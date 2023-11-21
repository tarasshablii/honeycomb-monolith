package dev.tarasshablii.monolith.opora.sponsors.domain.port;

import dev.tarasshablii.monolith.opora.sponsors.domain.model.Contact;

import java.util.List;

public interface ContactProvider {

	List<Contact> saveAll(List<Contact> contacts);
}