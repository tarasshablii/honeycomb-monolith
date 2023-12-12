package dev.tarasshablii.opora.monolith.initiatives.domain.model;

import lombok.Data;

@Data
public class Address extends Directions {

    private String street;
    private String town;
    private String directions;
}
