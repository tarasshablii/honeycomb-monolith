package dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.mapper;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.monolith.apigateway.provider.dto.SponsorDto;
import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = CommonMapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SponsorsDtoMapper {

    SponsorDto toDto(SponsorRequestDto requestDto);

    SponsorResponseDto toResponseDto(SponsorDto dto);

    List<SponsorResponseDto> toResponseDtoList(List<SponsorDto> dtos);
}
