package com.donalola.foodmenu.infraestructure.dao.factory;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import com.donalola.util.LocalDateTimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class FromDynamoEntityFoodMenuFactory implements FoodMenuFactory<FoodMenuDynamoEntity> {

    @Override
    public FoodMenu create(FoodMenuDynamoEntity foodMenuSource) {
        return FoodMenuEntityToDomainMapper.MAPPER.entityToDomain(foodMenuSource);
    }

    @Override
    public FoodMenuDynamoEntity create(FoodMenu foodMenu) {
        FoodMenuDynamoEntity entity = FoodMenuEntityToDomainMapper.MAPPER.domainToEntity(foodMenu);
        if (!Optional.ofNullable(entity.getCreatedDatetime()).isPresent()) {
            entity.setCreatedDatetime(LocalDateTimeUtil.getFrom(LocalDateTime.now(), LocalDateTimeUtil.PERU_ZONE_ID));
        }
        return entity;
    }
}
