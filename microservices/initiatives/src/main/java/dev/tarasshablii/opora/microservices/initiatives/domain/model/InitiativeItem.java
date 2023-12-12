package dev.tarasshablii.opora.microservices.initiatives.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class InitiativeItem {

    private String title;
    private String description;
    private UUID media;
    private ItemUnit unit;
    private int target;
    private int current;
}
