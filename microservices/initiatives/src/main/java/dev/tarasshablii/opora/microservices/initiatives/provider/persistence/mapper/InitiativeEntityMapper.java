package dev.tarasshablii.opora.microservices.initiatives.provider.persistence.mapper;

import dev.tarasshablii.opora.microservices.initiatives.config.CommonMapperConfig;
import dev.tarasshablii.opora.microservices.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.microservices.initiatives.provider.persistence.entity.InitiativeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CommonMapperConfig.class)
public interface InitiativeEntityMapper {

	InitiativeEntity toEntity(Initiative model);

	@Mapping(target = "progress", ignore = true)
	Initiative toModel(InitiativeEntity entity);

	List<Initiative> toModelList(List<InitiativeEntity> entities);
}