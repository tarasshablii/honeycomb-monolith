package dev.tarasshablii.monolith.opora.sponsors.domain.service;

import dev.tarasshablii.monolith.opora.common.exception.NotFoundException;
import dev.tarasshablii.monolith.opora.sponsors.domain.model.Contact;
import dev.tarasshablii.monolith.opora.sponsors.domain.model.Sponsor;
import dev.tarasshablii.monolith.opora.sponsors.domain.port.ContactProvider;
import dev.tarasshablii.monolith.opora.sponsors.domain.port.SponsorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SponsorService {

	public static final String NOT_FOUND_MESSAGE = "Sponsor with id [%s] not found";
	private final SponsorProvider sponsorProvider;
	private final ContactProvider contactProvider;

	public Sponsor create(Sponsor sponsor) {
		saveContacts(sponsor);
		return sponsorProvider.save(sponsor);
	}

	public void deleteById(UUID sponsorId) {
		if (!sponsorProvider.existsById(sponsorId)) {
			throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(sponsorId));
		}
		sponsorProvider.deleteById(sponsorId);
	}

	public Sponsor getById(UUID sponsorId) {
		return sponsorProvider.findById(sponsorId)
									 .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(sponsorId)));
	}

	public List<Sponsor> getAll() {
		return sponsorProvider.findAll();
	}

	public Sponsor updateById(UUID sponsorId, Sponsor update) {
		if (!sponsorProvider.existsById(sponsorId)) {
			throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(sponsorId));
		}
		update.setId(sponsorId);
		saveContacts(update);
		return sponsorProvider.save(update);
	}

	private void saveContacts(Sponsor sponsor) {
		List<Contact> contacts = sponsor.getContacts();
		contacts = contactProvider.saveAll(contacts);
		sponsor.setContacts(contacts);
	}

}