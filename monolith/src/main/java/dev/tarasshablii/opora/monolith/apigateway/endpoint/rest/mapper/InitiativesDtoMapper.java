package dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.mapper;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.AddressDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeRequestDirectionsInnerDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeRequestDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.NovaPoshtaDto;
import dev.tarasshablii.opora.monolith.apigateway.provider.dto.InitiativeDto;
import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;

import java.util.List;

@Mapper(config = CommonMapperConfig.class,
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface InitiativesDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "progress", ignore = true)
    InitiativeDto toDto(InitiativeRequestDto requestDto);

    InitiativeResponseDto toResponseDto(InitiativeDto dto);

    List<InitiativeResponseDto> toResponseDtoList(List<InitiativeDto> dto);

    @SubclassMappings(value = {
            @SubclassMapping(source = AddressDto.class, target = InitiativeDto.AddressDto.class),
            @SubclassMapping(source = NovaPoshtaDto.class, target = InitiativeDto.NovaPoshtaDto.class)
    })
    InitiativeDto.DirectionsDto toDirectionDto(InitiativeRequestDirectionsInnerDto requestDto);

    @SubclassMappings(value = {
            @SubclassMapping(source = InitiativeDto.AddressDto.class, target = AddressDto.class),
            @SubclassMapping(source = InitiativeDto.NovaPoshtaDto.class, target = NovaPoshtaDto.class)
    })
    @Mapping(target = "directionsType", ignore = true)
    InitiativeRequestDirectionsInnerDto toDirectionRequestDto(InitiativeDto.DirectionsDto dto);
}
