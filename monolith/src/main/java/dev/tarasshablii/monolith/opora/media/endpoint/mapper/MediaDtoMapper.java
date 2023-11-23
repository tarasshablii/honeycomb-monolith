package dev.tarasshablii.monolith.opora.media.endpoint.mapper;

import dev.tarasshablii.monolith.opora.apigateway.endpoint.rest.dto.MediaDto;
import dev.tarasshablii.monolith.opora.common.config.CommonMapperConfig;
import dev.tarasshablii.monolith.opora.media.domain.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface MediaDtoMapper {

	@Mapping(target = "resource", source = "media")
	@Mapping(target = "metadata.id", source = "id")
	@Mapping(target = "metadata.contentType", source = "contentType")
	Media toModel(MediaDto dto);

	@Mapping(target = "media", source = "resource")
	@Mapping(target = "id", source = "metadata.id")
	@Mapping(target = "contentType", source = "metadata.contentType")
	MediaDto toDto(Media model);
}