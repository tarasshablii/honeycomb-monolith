package dev.tarasshablii.opora.monolith.media;

import dev.tarasshablii.opora.monolith.media.domain.model.Media;
import dev.tarasshablii.opora.monolith.media.domain.model.Metadata;
import dev.tarasshablii.opora.monolith.media.provider.persistence.metadata.entity.MetadataEntity;
import org.springframework.core.io.Resource;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class MediaTestUtils {

    public static final UUID MEDIA_ID = UUID.fromString("ec2eb404-abdd-4ad6-894a-c480ea17ce75");

    public static Media defaultMedia() {
        return Media.builder()
                .metadata(Metadata.builder()
                        .id(MEDIA_ID)
                        .contentType("image/jpeg")
                        .build())
                .resource(mock(Resource.class))
                .build();
    }

    public static MetadataEntity defaultMetadataEntity() {
        return MetadataEntity.builder()
                .id(MEDIA_ID)
                .contentType("image/jpeg")
                .build();
    }

    private MediaTestUtils() {}
}
