package dev.tarasshablii.opora.monolith.sponsors.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Contact {
    private UUID id;
    private String firstName;
    private String lastName;
    private List<Phone> phones;
    private String email;
    private List<Link> links;
}
