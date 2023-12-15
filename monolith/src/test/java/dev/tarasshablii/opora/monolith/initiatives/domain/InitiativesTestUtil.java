package dev.tarasshablii.opora.monolith.initiatives.domain;

import dev.tarasshablii.opora.monolith.initiatives.domain.model.Contact;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.InitiativeItem;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.InitiativeSponsor;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.InitiativeStatus;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.ItemUnit;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Link;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Phone;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.PhoneType;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.PlatformType;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.AddressEntity;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.DirectionsEntity;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.InitiativeEntity;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.NovaPoshtaEntity;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;

public class InitiativesTestUtil {

    public static final UUID INITIATIVE_ID = UUID.fromString("93f9b055-a87a-4c7a-8bd1-bd5862182b75");
    public static final UUID MEDIA_ID = UUID.fromString("7632105a-3dfc-4e16-87fe-d743ba5ae922");
    public static final UUID SPONSOR_ID = UUID.fromString("ea49788f-bac6-4ba5-bf67-e6b233416a39");


    public static InitiativeEntity defaultInitiativeEntity() {
        return InitiativeEntity.builder()
                .id(INITIATIVE_ID)
                .title("title")
                .description("description")
                .media(MEDIA_ID)
                .status(InitiativeStatus.ACTIVE)
                .isUrgent(true)
                .sponsor(defaultSponsorEntity())
                .contacts(singletonList(defaultContact()))
                .directions(defaultDirections())
                .items(singletonList(defaultItem()))
                .build();
    }

    private static InitiativeItem defaultItem() {
        return InitiativeItem.builder()
                .title("title")
                .description("description")
                .media(MEDIA_ID)
                .unit(ItemUnit.COUNT)
                .target(100)
                .current(50)
                .build();
    }

    private static List<DirectionsEntity> defaultDirections() {
        return List.of(defaultAddressEntity(), defaultNovaPoshtaEntity());
    }

    private static NovaPoshtaEntity defaultNovaPoshtaEntity() {
        return NovaPoshtaEntity.builder()
                .town("town")
                .office(13)
                .recipient(defaultContact())
                .directions("directions")
                .build();
    }

    private static AddressEntity defaultAddressEntity() {
        return AddressEntity.builder()
                .street("street")
                .town("town")
                .directions("directions")
                .build();
    }

    private static Contact defaultContact() {
        return Contact.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phones(singletonList(defaultPhone()))
                .email("email@snail.con")
                .links(singletonList(defaultLink()))
                .build();
    }

    private static Link defaultLink() {
        return Link.builder()
                .url("url")
                .platform(PlatformType.FACEBOOK)
                .build();
    }

    private static Phone defaultPhone() {
        return Phone.builder()
                .number("number")
                .types(List.of(PhoneType.SIGNAL, PhoneType.WHATSAPP))
                .build();
    }

    private static InitiativeSponsor defaultSponsorEntity() {
        return InitiativeSponsor.builder()
                .id(SPONSOR_ID)
                .userName("userName")
                .name("name")
                .build();
    }

    private InitiativesTestUtil() {}
}
