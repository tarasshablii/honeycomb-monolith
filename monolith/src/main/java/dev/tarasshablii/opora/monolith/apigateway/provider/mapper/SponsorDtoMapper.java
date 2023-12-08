package dev.tarasshablii.opora.monolith.apigateway.provider.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CommonMapperConfig.class)
public interface SponsorDtoMapper {

	dev.tarasshablii.opora.monolith.sponsors.endpoint.dto.SponsorDto toInboundDto(
			dev.tarasshablii.opora.monolith.apigateway.provider.dto.SponsorDto model);

	dev.tarasshablii.opora.monolith.apigateway.provider.dto.SponsorDto toOutboundDto(
			dev.tarasshablii.opora.monolith.sponsors.endpoint.dto.SponsorDto dto);

	List<dev.tarasshablii.opora.monolith.apigateway.provider.dto.SponsorDto> toOutboundDtoList(
			List<dev.tarasshablii.opora.monolith.sponsors.endpoint.dto.SponsorDto> sponsors);
}
