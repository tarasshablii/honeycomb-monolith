package dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.mapper;

import dev.tarasshablii.opora.microservices.initiatives.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.Address;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.Directions;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.NovaPoshta;
import dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.dto.*;
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
	Directions toDirectionModel(InitiativeServiceRequestDirectionsInnerDto dto);

	@SubclassMappings(value = {
			@SubclassMapping(source = Address.class, target = AddressDto.class),
			@SubclassMapping(source = NovaPoshta.class, target = NovaPoshtaDto.class)
	})
	@Mapping(target = "directionsType", ignore = true)
	InitiativeServiceRequestDirectionsInnerDto toDirectionDto(Directions model);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "progress", ignore = true)
	Initiative toModel(InitiativeServiceRequestDto dto);

	InitiativeServiceResponseDto toDto(Initiative model);

	List<InitiativeServiceResponseDto> toDtoList(List<Initiative> models);
}