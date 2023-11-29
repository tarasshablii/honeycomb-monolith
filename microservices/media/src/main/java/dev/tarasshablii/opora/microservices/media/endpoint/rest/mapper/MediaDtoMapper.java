package dev.tarasshablii.opora.microservices.media.endpoint.rest.mapper;

import dev.tarasshablii.opora.microservices.media.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.media.domain.model.Media;
import dev.tarasshablii.opora.microservices.media.endpoint.rest.dto.MediaDto;
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