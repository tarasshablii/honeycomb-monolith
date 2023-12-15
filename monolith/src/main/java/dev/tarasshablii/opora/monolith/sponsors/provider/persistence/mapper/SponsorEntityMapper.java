package dev.tarasshablii.opora.monolith.sponsors.provider.persistence.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.sponsors.domain.model.Sponsor;
import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity.SponsorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = CommonMapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SponsorEntityMapper {

    SponsorEntity toEntity(Sponsor model);

    Sponsor toModel(SponsorEntity entity);

    List<Sponsor> toModelList(List<SponsorEntity> entities);
}
