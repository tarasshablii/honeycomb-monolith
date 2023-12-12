package dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity;

import dev.tarasshablii.opora.monolith.sponsors.domain.model.PlatformType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@Table(name = "links")
@NoArgsConstructor
@AllArgsConstructor
public class LinkEmbeddable {

    private String url;
    private PlatformType platform;
}
