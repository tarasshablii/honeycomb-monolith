package dev.tarasshablii.opora.microservices.initiatives.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Contact {

    private String firstName;
    private String lastName;
    private List<Phone> phones;
    private String email;
    private List<Link> links;
}
