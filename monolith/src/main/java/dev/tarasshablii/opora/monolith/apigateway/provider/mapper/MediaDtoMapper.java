package dev.tarasshablii.opora.monolith.apigateway.provider.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface MediaDtoMapper {

    dev.tarasshablii.opora.monolith.media.endpoint.dto.MediaDto toInboundDto(
            dev.tarasshablii.opora.monolith.apigateway.provider.dto.MediaDto requestDto);

    dev.tarasshablii.opora.monolith.apigateway.provider.dto.MediaDto toOutboundDto(
            dev.tarasshablii.opora.monolith.media.endpoint.dto.MediaDto dto);
}
