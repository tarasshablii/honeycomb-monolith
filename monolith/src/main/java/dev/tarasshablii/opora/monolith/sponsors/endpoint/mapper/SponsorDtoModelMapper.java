package dev.tarasshablii.opora.monolith.sponsors.endpoint.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.sponsors.domain.model.Sponsor;
import dev.tarasshablii.opora.monolith.sponsors.endpoint.dto.SponsorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CommonMapperConfig.class)
public interface SponsorDtoModelMapper {

    Sponsor toModel(SponsorDto dto);

    SponsorDto toDto(Sponsor model);

    List<SponsorDto> toDtoList(List<Sponsor> sponsors);
}
