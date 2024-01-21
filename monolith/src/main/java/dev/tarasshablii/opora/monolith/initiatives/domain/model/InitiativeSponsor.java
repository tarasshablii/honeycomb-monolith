package dev.tarasshablii.opora.monolith.initiatives.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InitiativeSponsor {

    private UUID id;
    private String userName;
    private String name;
}
