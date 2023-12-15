package dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity;

import dev.tarasshablii.opora.monolith.sponsors.domain.model.PhoneType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@Embeddable
@Table(name = "phones")
@NoArgsConstructor
@AllArgsConstructor
public class PhoneEmbeddable {

    private String number;
    private Set<PhoneType> types;
}
