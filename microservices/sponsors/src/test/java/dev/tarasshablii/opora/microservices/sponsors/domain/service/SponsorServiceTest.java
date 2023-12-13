package dev.tarasshablii.opora.microservices.sponsors.domain.service;

import dev.tarasshablii.opora.microservices.sponsors.domain.exception.NotFoundException;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.Sponsor;
import dev.tarasshablii.opora.microservices.sponsors.domain.port.ContactProvider;
import dev.tarasshablii.opora.microservices.sponsors.domain.port.SponsorProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static dev.tarasshablii.opora.microservices.sponsors.SponsorTestUtils.SPONSOR_ID;
import static dev.tarasshablii.opora.microservices.sponsors.SponsorTestUtils.defaultSponsor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class SponsorServiceTest {

    @Mock
    private SponsorProvider sponsorProvider;
    @Mock
    private ContactProvider contactProvider;
    @InjectMocks
    private SponsorService service;

    private Sponsor sponsor;

    @BeforeEach
    void setUp() {
        sponsor = defaultSponsor();
    }

    @Test
    void create_shouldSaveContactsAndSponsor() {
        given(contactProvider.saveAll(anyList())).willReturn(sponsor.getContacts());

        service.create(sponsor);

        then(contactProvider).should().saveAll(sponsor.getContacts());
        then(sponsorProvider).should().save(sponsor);
    }

    @Test
    void deleteById_shouldThrownNotFound_givenSponsorDoesNotExist() {
        given(sponsorProvider.existsById(any(UUID.class))).willReturn(false);

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.deleteById(SPONSOR_ID));

        then(sponsorProvider).should(never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteById_shouldDeleteExistingSponsor() {
        given(sponsorProvider.existsById(any(UUID.class))).willReturn(true);

        service.deleteById(SPONSOR_ID);

        then(sponsorProvider).should().deleteById(SPONSOR_ID);
    }

    @Test
    void getById_shouldThrowNotFound_givenSponsorDoesNotExist() {
        given(sponsorProvider.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.getById(SPONSOR_ID));
    }

    @Test
    void getById_shouldReturnExistingSponsor() {
        given(sponsorProvider.findById(any(UUID.class))).willReturn(Optional.of(sponsor));

        assertThat(service.getById(SPONSOR_ID)).isEqualTo(sponsor);
        then(sponsorProvider).should().findById(SPONSOR_ID);
    }

    @Test
    void updateById_shouldThrowNotFound_givenSponsorDoesNotExist() {
        given(sponsorProvider.existsById(any(UUID.class))).willReturn(false);

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.updateById(SPONSOR_ID, sponsor));

        then(sponsorProvider).should(never()).save(any(Sponsor.class));
        then(contactProvider).should(never()).saveAll(anyList());
    }

    @Test
    void updateById_shouldReplaceExistingSponsor() {
        var update = defaultSponsor();
        update.setName("new sponsor");
        given(sponsorProvider.existsById(any(UUID.class))).willReturn(true);
        given(contactProvider.saveAll(anyList())).willReturn(update.getContacts());

        service.updateById(SPONSOR_ID, update);

        then(sponsorProvider).should().save(update);
        then(contactProvider).should().saveAll(update.getContacts());
    }

}
