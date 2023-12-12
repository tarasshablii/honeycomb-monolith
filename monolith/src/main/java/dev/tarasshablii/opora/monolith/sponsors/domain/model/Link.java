package dev.tarasshablii.opora.monolith.sponsors.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Link {
    private String url;
    private PlatformType platform;
}
