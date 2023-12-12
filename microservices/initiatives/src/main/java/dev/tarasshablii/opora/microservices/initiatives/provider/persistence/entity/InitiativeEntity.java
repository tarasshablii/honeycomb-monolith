package dev.tarasshablii.opora.microservices.initiatives.provider.persistence.entity;

import dev.tarasshablii.opora.microservices.initiatives.domain.model.Contact;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.InitiativeItem;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.InitiativeSponsor;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.InitiativeStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
@TypeAlias("initiative")
public class InitiativeEntity {

    @Id
    private UUID id;
    private String title;
    private String description;
    private UUID media;
    private InitiativeStatus status;
    private Boolean isUrgent;
    private InitiativeSponsor sponsor;
    private List<Contact> contacts;
    private List<DirectionsEntity> directions;
    private List<InitiativeItem> items;
}
