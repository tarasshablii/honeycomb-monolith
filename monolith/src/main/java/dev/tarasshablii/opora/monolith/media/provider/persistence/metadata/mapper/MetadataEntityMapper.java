package dev.tarasshablii.opora.monolith.media.provider.persistence.metadata.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.media.domain.model.Metadata;
import dev.tarasshablii.opora.monolith.media.provider.persistence.metadata.entity.MetadataEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface MetadataEntityMapper {

    Metadata toModel(MetadataEntity entity);

    MetadataEntity toEntity(Metadata model);
}
