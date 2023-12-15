package dev.tarasshablii.opora.microservices.media.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@Builder
public class Media {
    private Resource resource;
    private Metadata metadata;

    public void generateId() {
        if (isNull(metadata)) {
            metadata = Metadata.builder().build();
        }
        metadata.setId(UUID.randomUUID());
    }
}
