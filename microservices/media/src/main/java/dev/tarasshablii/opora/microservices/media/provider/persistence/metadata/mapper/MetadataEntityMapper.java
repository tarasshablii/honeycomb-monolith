package dev.tarasshablii.opora.microservices.media.provider.persistence.metadata.mapper;

import dev.tarasshablii.opora.microservices.media.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.media.domain.model.Metadata;
import dev.tarasshablii.opora.microservices.media.provider.persistence.metadata.entity.MetadataEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface MetadataEntityMapper {

    Metadata toModel(MetadataEntity entity);

    MetadataEntity toEntity(Metadata model);
}
