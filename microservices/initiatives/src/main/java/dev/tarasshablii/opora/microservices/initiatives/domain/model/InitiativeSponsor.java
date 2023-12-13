package dev.tarasshablii.opora.microservices.initiatives.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Data
@Builder
public class InitiativeSponsor {

    @Indexed
    private UUID id;
    private String userName;
    private String name;
}
