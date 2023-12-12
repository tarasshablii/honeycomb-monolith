package dev.tarasshablii.opora.monolith.apigateway.provider.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class InitiativeDto {

    private UUID id;
    private String title;
    private String description;
    private UUID media;
    private StatusEnum status;
    private Boolean isUrgent;
    private Integer progress;
    private InitiativeSponsorDto sponsor;
    private List<ContactDto> contacts;
    private List<DirectionsDto> directions;
    private List<ItemDto> items;

    public enum StatusEnum {
        ACTIVE, COMPLETED, CANCELED
    }

    @Data
    public static class InitiativeSponsorDto {
        private UUID id;
        private String userName;
        private String name;
    }

    @Data
    public static class ContactDto {
        private String firstName;
        private String lastName;
        private List<PhoneDto> phones;
        private String email;
        private List<LinkDto> links;
    }

    @Data
    public static class PhoneDto {
        private String number;
        private List<PhoneType> types;
    }

    public enum PhoneType {
        CELLULAR, VIBER, TELEGRAM, WHATSAPP, SIGNAL
    }

    @Data
    public static class LinkDto {
        private String url;
        private PlatformType platform;
    }

    public enum PlatformType {
        FACEBOOK, X, INSTAGRAM, TELEGRAM, WEBSITE
    }

    public static abstract class DirectionsDto {
    }

    @Data
    public static class AddressDto extends DirectionsDto {
        private String street;
        private String town;
        private String directions;
    }

    @Data
    public static class NovaPoshtaDto extends DirectionsDto {
        private String town;
        private Integer office;
        private ContactDto recipient;
        private String directions;
    }

    @Data
    public static class ItemDto {
        private String title;
        private String description;
        private UUID media;
        private Unit unit;
        private Integer target;
        private Integer current = 0;
    }

    public enum Unit {
        COUNT, KG, L, PACK
    }

}
