package dev.tarasshablii.opora.microservices.sponsors.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Sponsor {
    private UUID id;
    private String userName;
    private String name;
    private String description;
    private UUID media;
    private List<Contact> contacts;
}
