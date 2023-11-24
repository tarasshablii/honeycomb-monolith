package dev.tarasshablii.opora.monolith.initiatives.endpoint.mapper;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.*;
import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Address;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Directions;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.NovaPoshta;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CommonMapperConfig.class,
		subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION
)
public interface InitiativesDtoMapper {

	@SubclassMappings(value = {
			@SubclassMapping(source = AddressDto.class, target = Address.class),
			@SubclassMapping(source = NovaPoshtaDto.class, target = NovaPoshta.class)
	})
	Directions toDirectionModel(InitiativeRequestDirectionsInnerDto dto);

	@SubclassMappings(value = {
			@SubclassMapping(source = Address.class, target = AddressDto.class),
			@SubclassMapping(source = NovaPoshta.class, target = NovaPoshtaDto.class)
	})
	@Mapping(target = "directionsType", ignore = true)
	InitiativeRequestDirectionsInnerDto toDirectionDto(Directions model);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "progress", ignore = true)
	Initiative toModel(InitiativeRequestDto dto);

	InitiativeResponseDto toDto(Initiative model);

	List<InitiativeResponseDto> toDtoList(List<Initiative> models);
}