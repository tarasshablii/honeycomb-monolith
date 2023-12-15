package dev.tarasshablii.opora.monolith.initiatives.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Phone {

    private String number;
    private List<PhoneType> types;
}
