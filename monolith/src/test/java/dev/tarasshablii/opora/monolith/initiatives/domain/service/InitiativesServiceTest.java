package dev.tarasshablii.opora.monolith.initiatives.domain.service;

import dev.tarasshablii.opora.monolith.common.exception.NotFoundException;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.monolith.initiatives.domain.port.InitiativeProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class InitiativesServiceTest {

    private final UUID id = UUID.randomUUID();
    @Mock
    private Initiative initiative;
    @Mock
    private InitiativeProvider provider;
    @InjectMocks
    private InitiativesService service;

    @Test
    void getById_shouldThrowNoFound_givenNoInitiativeExists() {
        given(provider.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.getById(id));
    }

    @Test
    void getById_shouldReturnExistingInitiative() {
        given(provider.findById(any(UUID.class))).willReturn(Optional.of(initiative));

        assertThat(service.getById(id)).isEqualTo(initiative);
    }

    @Test
    void updateById_shouldThrowNoFound_givenNoInitiativeExists() {
        given(provider.existsById(any(UUID.class))).willReturn(false);

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.updateById(id, initiative));

        then(provider).should(never()).save(any(Initiative.class));
    }

    @Test
    void updateById_shouldUpdateExistingInitiative() {
        given(provider.existsById(any(UUID.class))).willReturn(true);

        service.updateById(id, initiative);

        then(provider).should().save(initiative);
    }

    @Test
    void deleteById_shouldThrowNoFound_givenNoInitiativeExists() {
        given(provider.existsById(any(UUID.class))).willReturn(false);

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.deleteById(id));

        then(provider).should(never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteById_shouldUpdateExistingInitiative() {
        given(provider.existsById(any(UUID.class))).willReturn(true);

        service.deleteById(id);

        then(provider).should().deleteById(id);
    }
}
