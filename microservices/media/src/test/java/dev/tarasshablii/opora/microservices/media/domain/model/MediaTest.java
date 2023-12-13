package dev.tarasshablii.opora.microservices.media.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class MediaTest {

    @Test
    void generateId_shouldGenerateRandomId() {
        var media = Media.builder()
                .metadata(Metadata.builder().build())
                .build();

        media.generateId();

        assertThat(media.getMetadata().getId()).isNotNull();
    }

    @Test
    void generateId_shouldCreateMetadata_givenNoneExists() {
        var media = Media.builder().build();

        media.generateId();

        assertThat(media.getMetadata()).isNotNull();
        assertThat(media.getMetadata().getId()).isNotNull();
    }
}
