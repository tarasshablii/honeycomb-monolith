package dev.tarasshablii.opora.monolith.initiatives.provider.persistence.mapper;

import dev.tarasshablii.opora.monolith.common.config.CommonMapperConfig;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Address;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Directions;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.Initiative;
import dev.tarasshablii.opora.monolith.initiatives.domain.model.NovaPoshta;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.AddressEntity;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.DirectionsEntity;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.InitiativeEntity;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.NovaPoshtaEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CommonMapperConfig.class,
		subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface InitiativeEntityMapper {

	@SubclassMappings(value = {
			@SubclassMapping(source = AddressEntity.class, target = Address.class),
			@SubclassMapping(source = NovaPoshtaEntity.class, target = NovaPoshta.class)
	})
	Directions toDirectionsModel(DirectionsEntity entity);

	@SubclassMappings(value = {
			@SubclassMapping(source = Address.class, target = AddressEntity.class),
			@SubclassMapping(source = NovaPoshta.class, target = NovaPoshtaEntity.class)
	})
	DirectionsEntity toDirectionsEntity(Directions model);

	InitiativeEntity toEntity(Initiative model);

	@Mapping(target = "progress", ignore = true)
	Initiative toModel(InitiativeEntity entity);

	List<Initiative> toModelList(List<InitiativeEntity> entities);
}
