package dev.tarasshablii.opora.monolith.media.domain.service;

import dev.tarasshablii.opora.monolith.common.exception.NotFoundException;
import dev.tarasshablii.opora.monolith.media.domain.model.Media;
import dev.tarasshablii.opora.monolith.media.domain.model.Metadata;
import dev.tarasshablii.opora.monolith.media.domain.port.MediaProvider;
import dev.tarasshablii.opora.monolith.media.domain.port.MetadataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static dev.tarasshablii.opora.monolith.media.MediaTestUtils.MEDIA_ID;
import static dev.tarasshablii.opora.monolith.media.MediaTestUtils.defaultMedia;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaProvider mediaProvider;
    @Mock
    private MetadataProvider metadataProvider;
    @InjectMocks
    private MediaService service;

    private Media media;

    @BeforeEach
    void setUp() {
        media = defaultMedia();
    }

    @Test
    void create_shouldGenerateNewId_shouldSaveMediaAndMetadata() {
        given(metadataProvider.save(any(Metadata.class))).willReturn(media.getMetadata());

        assertThat(service.create(media)).isNotEqualTo(MEDIA_ID);

        then(mediaProvider).should().save(media);
        then(metadataProvider).should().save(media.getMetadata());
    }

    @Test
    void deleteById_shouldThrowNotFound_givenNoMetadataExists() {
        given(metadataProvider.existsById(any(UUID.class))).willReturn(false);

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.deleteById(MEDIA_ID));

        then(metadataProvider).should().existsById(MEDIA_ID);
        then(mediaProvider).should(never()).deleteById(any(UUID.class));
        then(metadataProvider).should(never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteById_shouldDeleteExistingMediaAndMetadata() {
        given(metadataProvider.existsById(any(UUID.class))).willReturn(true);

        service.deleteById(MEDIA_ID);

        then(mediaProvider).should().deleteById(MEDIA_ID);
        then(metadataProvider).should().deleteById(MEDIA_ID);
    }

    @Test
    void getById_shouldThrowNotFound_givenNoMetadataExists() {
        given(metadataProvider.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.getById(MEDIA_ID));

        then(metadataProvider).should().findById(MEDIA_ID);
        then(mediaProvider).should(never()).findById(any(UUID.class));
    }

    @Test
    void getById_shouldThrowNotFound_givenNoMediaExist() {
        given(metadataProvider.findById(any(UUID.class))).willReturn(Optional.of(media.getMetadata()));
        given(mediaProvider.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.getById(MEDIA_ID));

        then(metadataProvider).should().findById(MEDIA_ID);
        then(mediaProvider).should().findById(MEDIA_ID);
    }

    @Test
    void getById_shouldReturnExistingMedia() {
        given(metadataProvider.findById(any(UUID.class))).willReturn(Optional.of(media.getMetadata()));
        given(mediaProvider.findById(any(UUID.class))).willReturn(Optional.of(media));

        assertThat(service.getById(MEDIA_ID)).isEqualTo(media);

        then(metadataProvider).should().findById(MEDIA_ID);
        then(mediaProvider).should().findById(MEDIA_ID);
    }

    @Test
    void update_shouldThrowNotFound_givenNoMetadataExists() {
        given(metadataProvider.existsById(any(UUID.class))).willReturn(false);

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> service.update(media));

        then(metadataProvider).should().existsById(MEDIA_ID);
        then(mediaProvider).should(never()).save(any(Media.class));
        then(metadataProvider).should(never()).save(any(Metadata.class));
    }

    @Test
    void update_shouldUpdateExistingMedia() {
        given(metadataProvider.existsById(any(UUID.class))).willReturn(true);

        service.update(media);

        then(mediaProvider).should().save(media);
        then(metadataProvider).should().save(media.getMetadata());
    }


}
