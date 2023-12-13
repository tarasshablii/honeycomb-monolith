package dev.tarasshablii.opora.microservices.media.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Metadata {
    private UUID id;
    private String contentType;
}
