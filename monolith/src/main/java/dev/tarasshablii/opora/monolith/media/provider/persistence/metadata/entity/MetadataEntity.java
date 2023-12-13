package dev.tarasshablii.opora.monolith.media.provider.persistence.metadata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "metadata")
@NoArgsConstructor
@AllArgsConstructor
public class MetadataEntity {

    @Id
    private UUID id;
    private String contentType;
}
