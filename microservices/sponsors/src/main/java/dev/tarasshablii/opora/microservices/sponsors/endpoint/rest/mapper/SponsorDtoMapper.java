package dev.tarasshablii.opora.microservices.sponsors.endpoint.rest.mapper;

import dev.tarasshablii.opora.microservices.sponsors.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.sponsors.domain.model.Sponsor;
import dev.tarasshablii.opora.microservices.sponsors.endpoint.rest.dto.SponsorServiceRequestDto;
import dev.tarasshablii.opora.microservices.sponsors.endpoint.rest.dto.SponsorServiceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = CommonMapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SponsorDtoMapper {

    @Mapping(target = "id", ignore = true)
    Sponsor toModel(SponsorServiceRequestDto dto);

    SponsorServiceResponseDto toDto(Sponsor model);

    List<SponsorServiceResponseDto> toDtoList(List<Sponsor> sponsors);
}
