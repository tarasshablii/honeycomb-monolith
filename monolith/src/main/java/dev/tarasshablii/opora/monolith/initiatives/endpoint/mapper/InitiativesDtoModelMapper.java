package dev.tarasshablii.opora.monolith.initiatives.endpoint.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Address;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Directions;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.NovaPoshta;
import dev.tarasshablii.opora.monolith.initiatives.endpoint.dto.InitiativeDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CommonMapperConfig.class,
		subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION
)
public interface InitiativesDtoModelMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "progress", ignore = true)
	Initiative toModel(InitiativeDto dto);

	InitiativeDto toDto(Initiative model);

	List<InitiativeDto> toDtoList(List<Initiative> models);

	@SubclassMappings(value = {
			@SubclassMapping(source = InitiativeDto.AddressDto.class, target = Address.class),
			@SubclassMapping(source = InitiativeDto.NovaPoshtaDto.class, target = NovaPoshta.class)
	})
	Directions toDirectionModel(InitiativeDto.DirectionsDto dto);

	@SubclassMappings(value = {
			@SubclassMapping(source = Address.class, target = InitiativeDto.AddressDto.class),
			@SubclassMapping(source = NovaPoshta.class, target = InitiativeDto.NovaPoshtaDto.class)
	})
	InitiativeDto.DirectionsDto toDirectionDto(Directions model);
}
