package dev.tarasshablii.opora.microservices.sponsors.provider.persistence.mapper;

import dev.tarasshablii.opora.microservices.sponsors.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.Contact;
import dev.tarasshablii.opora.microservices.sponsors.provider.persistence.entity.ContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = CommonMapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactEntityMapper {

    List<ContactEntity> toEntityList(List<Contact> models);

    List<Contact> toModelList(List<ContactEntity> entities);
}
