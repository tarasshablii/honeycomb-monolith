package dev.tarasshablii.opora.monolith.sponsors.endpoint.mapper;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.SponsorRequestDto;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.sponsors.domain.model.Sponsor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = CommonMapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SponsorDtoMapper {

	@Mapping(target = "id", ignore = true)
	Sponsor toModel(SponsorRequestDto dto);

	SponsorResponseDto toDto(Sponsor model);

	List<SponsorResponseDto> toDtoList(List<Sponsor> sponsors);
}
