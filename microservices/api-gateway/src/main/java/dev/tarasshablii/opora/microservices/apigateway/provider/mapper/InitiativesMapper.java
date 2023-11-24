package dev.tarasshablii.opora.microservices.apigateway.provider.mapper;

import dev.tarasshablii.opora.microservices.apigateway.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.*;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.InitiativeServiceRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.InitiativeServiceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CommonMapperConfig.class)
public interface InitiativesMapper {

	InitiativeServiceRequestDto toServiceRequestDto(InitiativeRequestDto gatewayRequestDto);

	InitiativeResponseDto toGatewayResponseDto(InitiativeServiceResponseDto serviceResponseDto);

	List<InitiativeResponseDto> toGatewayResponseList(List<InitiativeServiceResponseDto> serviceResponseDto);

	AddressDto toGatewayAddressDto(dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.AddressDto dto);

	dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.AddressDto toServiceAddressDto(AddressDto dto);

	NovaPoshtaDto toGatewayNPDto(dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.NovaPoshtaDto dto);

	dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.NovaPoshtaDto toServiceNPDto(NovaPoshtaDto dto);

	default dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.InitiativeServiceRequestDirectionsInnerDto toServiceDirection(
			InitiativeRequestDirectionsInnerDto dto) {
		if (dto instanceof AddressDto addressDto) {
			return toServiceAddressDto(addressDto);
		} else if (dto instanceof NovaPoshtaDto novaPoshtaDto) {
			return toServiceNPDto(novaPoshtaDto);
		} else {
			throw new IllegalArgumentException("Unknown implementation of Directions");
		}
	}

	default InitiativeRequestDirectionsInnerDto toGatewayDirection(
			dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.InitiativeServiceRequestDirectionsInnerDto dto) {
		if (dto instanceof dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.AddressDto addressDto) {
			return toGatewayAddressDto(addressDto);
		} else if (dto instanceof dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.initiatives.NovaPoshtaDto novaPoshtaDto) {
			return toGatewayNPDto(novaPoshtaDto);
		} else {
			throw new IllegalArgumentException("Unknown implementation of Directions");
		}
	}

}