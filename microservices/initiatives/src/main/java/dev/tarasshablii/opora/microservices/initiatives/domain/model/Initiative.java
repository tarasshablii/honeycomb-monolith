package dev.tarasshablii.opora.microservices.initiatives.domain.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Data
public class Initiative {

    private UUID id;
    private String title;
    private String description;
    private UUID media;
    private InitiativeStatus status;
    private Boolean isUrgent;
    private Integer progress;
    private InitiativeSponsor sponsor;
    private List<Contact> contacts;
    private List<Directions> directions;
    private List<InitiativeItem> items;

    public Integer getProgress() {
        if (isEmpty(items)) {
            return 0;
        }
        int totalTarget = items.stream().mapToInt(InitiativeItem::getTarget).sum();
        int totalCurrent = items.stream().mapToInt(InitiativeItem::getCurrent).sum();
        return totalTarget > 0 ? (int) ((double) totalCurrent / totalTarget * 100) : 0;
    }
}
