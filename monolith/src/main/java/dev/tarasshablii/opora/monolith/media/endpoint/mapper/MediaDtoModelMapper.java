package dev.tarasshablii.opora.monolith.media.endpoint.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.media.domain.model.Media;
import dev.tarasshablii.opora.monolith.media.endpoint.dto.MediaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface MediaDtoModelMapper {

	@Mapping(target = "resource", source = "media")
	@Mapping(target = "metadata.id", source = "id")
	@Mapping(target = "metadata.contentType", source = "contentType")
	Media toModel(MediaDto dto);

	@Mapping(target = "media", source = "resource")
	@Mapping(target = "id", source = "metadata.id")
	@Mapping(target = "contentType", source = "metadata.contentType")
	MediaDto toDto(Media model);
}
