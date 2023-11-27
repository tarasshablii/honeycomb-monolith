package dev.tarasshablii.opora.microservices.sponsors.domain.port;

import dev.tarasshablii.opora.microservices.sponsors.domain.model.Contact;

import java.util.List;

public interface ContactProvider {

	List<Contact> saveAll(List<Contact> contacts);
}