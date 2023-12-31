package dev.tarasshablii.opora.microservices.media.endpoint.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.util.UUID;

@Data
@Builder
public class MediaDto {
    private Resource media;
    private UUID id;
    private String contentType;
}
