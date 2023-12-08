package dev.tarasshablii.opora.monolith.apigateway.provider.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CommonMapperConfig.class,
		subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface InitiativeDtoMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "progress", ignore = true)
	dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto toInboundDto(
			dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto requestDto);

	dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto toOutboundDto(
			dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto dto);

	List<dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto> toOutboundDtoList(
			List<dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto> dto);

	@SubclassMappings(value = {
			@SubclassMapping(
					source = dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto.AddressDto.class,
					target = dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto.AddressDto.class
			),
			@SubclassMapping(
					source = dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto.NovaPoshtaDto.class,
					target = dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto.NovaPoshtaDto.class
			)
	})
	dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto.DirectionsDto toDirectionInboundDto(
			dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto.DirectionsDto requestDto);

	@SubclassMappings(value = {
			@SubclassMapping(
					source = dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto.AddressDto.class,
					target = dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto.AddressDto.class
			),
			@SubclassMapping(
					source = dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto.NovaPoshtaDto.class,
					target = dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto.NovaPoshtaDto.class
			)
	})
	dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto.DirectionsDto toDirectionOutboundDto(
			dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto.DirectionsDto dto);
}
