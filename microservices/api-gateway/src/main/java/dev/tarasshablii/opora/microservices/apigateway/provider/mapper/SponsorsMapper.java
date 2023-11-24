package dev.tarasshablii.opora.microservices.apigateway.provider.mapper;

import dev.tarasshablii.opora.microservices.apigateway.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.sponsors.SponsorServiceRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.dto.sponsors.SponsorServiceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CommonMapperConfig.class)
public interface SponsorsMapper {

	SponsorServiceRequestDto toServiceRequestDto(SponsorRequestDto gatewayRequestDto);

	SponsorResponseDto toGatewayResponseDto(SponsorServiceResponseDto serviceResponseDto);

	List<SponsorResponseDto> toGatewayResponseList(List<SponsorServiceResponseDto> serviceResponseDto);

}