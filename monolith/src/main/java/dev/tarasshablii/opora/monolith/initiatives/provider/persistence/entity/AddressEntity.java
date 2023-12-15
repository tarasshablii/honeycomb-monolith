package dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@TypeAlias("address")
public class AddressEntity extends DirectionsEntity {

    private String street;
    private String town;
    private String directions;
}
