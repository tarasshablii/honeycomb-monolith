package dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Data
@Builder
public class InitiativeSponsorEntity {

    @Indexed
    private UUID id;
    private String userName;
    private String name;
}
