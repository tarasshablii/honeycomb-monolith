package dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity;

import dev.tarasshablii.opora.monolith.initiatives.domain.model.Contact;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.InitiativeItem;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.InitiativeStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Builder
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
    private InitiativeSponsorEntity sponsor;
    private List<Contact> contacts;
    private List<DirectionsEntity> directions;
    private List<InitiativeItem> items;
}
