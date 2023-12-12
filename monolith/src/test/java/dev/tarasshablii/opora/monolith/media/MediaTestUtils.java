package dev.tarasshablii.opora.monolith.media;

import dev.tarasshablii.opora.monolith.media.domain.model.Media;
import dev.tarasshablii.opora.monolith.media.domain.model.Metadata;
import org.springframework.core.io.Resource;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class MediaTestUtils {

    public static final UUID MEDIA_ID = UUID.randomUUID();

    public static Media defaultMedia() {
        return Media.builder()
                .metadata(Metadata.builder()
                        .id(MEDIA_ID)
                        .contentType("image/jpeg")
                        .build())
                .resource(mock(Resource.class))
                .build();
    }

    private MediaTestUtils() {}
}
