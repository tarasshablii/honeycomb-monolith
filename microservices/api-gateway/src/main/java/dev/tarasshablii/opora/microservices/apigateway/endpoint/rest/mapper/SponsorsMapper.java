package dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.mapper;

import dev.tarasshablii.opora.microservices.apigateway.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.dto.SponsorServiceRequestDto;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.dto.SponsorServiceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CommonMapperConfig.class)
public interface SponsorsMapper {

    SponsorServiceRequestDto toServiceRequestDto(SponsorRequestDto gatewayRequestDto);

    SponsorResponseDto toGatewayResponseDto(SponsorServiceResponseDto serviceResponseDto);

    List<SponsorResponseDto> toGatewayResponseList(List<SponsorServiceResponseDto> serviceResponseDto);

}
