package dev.tarasshablii.opora.microservices.initiatives.domain.model;

import lombok.Data;

@Data
public class NovaPoshta extends Directions {

    private String town;
    private Integer office;
    private Contact recipient;
    private String directions;
}
