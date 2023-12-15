package dev.tarasshablii.opora.microservices.sponsors;

import dev.tarasshablii.opora.microservices.sponsors.domain.model.Contact;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.Link;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.Phone;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.PhoneType;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.PlatformType;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.Sponsor;
import dev.tarasshablii.opora.microservices.sponsors.provider.persistence.entity.ContactEntity;
import dev.tarasshablii.opora.microservices.sponsors.provider.persistence.entity.LinkEmbeddable;
import dev.tarasshablii.opora.microservices.sponsors.provider.persistence.entity.PhoneEmbeddable;
import dev.tarasshablii.opora.microservices.sponsors.provider.persistence.entity.SponsorEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.singletonList;

public class SponsorTestUtils {

    public static final UUID SPONSOR_ID = UUID.fromString("0101fa18-17b7-4c29-91bc-1238f555499d");
    public static final UUID CONTACT_ID = UUID.fromString("e5298900-fa9f-4a11-a0e5-6dea1eabccef");
    public static final UUID MEDIA_ID = UUID.fromString("28f9ff91-773a-49c6-bdbb-0536571d6d99");

    public static SponsorEntity defaultSponsorEntity() {
        return SponsorEntity.builder()
                .id(SPONSOR_ID)
                .userName("userName")
                .name("name")
                .description("description")
                .media(MEDIA_ID.toString())
                .contacts(singletonList(defaultContactEntity()))
                .build();
    }

    public static ContactEntity defaultContactEntity() {
        return ContactEntity.builder()
                .id(CONTACT_ID)
                .firstName("firstName")
                .lastName("lastName")
                .email("email@snail.con")
                .phones(singletonList(defaultPhoneEmbeddable()))
                .links(singletonList(defaultLinkEmbeddable()))
                .build();
    }

    private static LinkEmbeddable defaultLinkEmbeddable() {
        return LinkEmbeddable.builder()
                .url("url")
                .platform(PlatformType.FACEBOOK)
                .build();
    }

    private static PhoneEmbeddable defaultPhoneEmbeddable() {
        return PhoneEmbeddable.builder()
                .number("number")
                .types(Set.of(PhoneType.SIGNAL, PhoneType.WHATSAPP))
                .build();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }

    public static Sponsor defaultSponsor() {
        return Sponsor.builder()
                .id(SPONSOR_ID)
                .userName("userName")
                .name("name")
                .description("description")
                .media(UUID.randomUUID())
                .contacts(singletonList(defaultContact()))
                .build();
    }

    public static Contact defaultContact() {
        return Contact.builder()
                .id(UUID.randomUUID())
                .firstName("firstName")
                .lastName("lastName")
                .phones(singletonList(defaultPhone()))
                .email("email")
                .links(singletonList(defaultLink()))
                .build();
    }

    public static Link defaultLink() {
        return Link.builder()
                .url("url")
                .platform(PlatformType.FACEBOOK)
                .build();
    }

    public static Phone defaultPhone() {
        return Phone.builder()
                .number("number")
                .types(List.of(PhoneType.SIGNAL, PhoneType.WHATSAPP))
                .build();
    }

    private SponsorTestUtils() {}
}
