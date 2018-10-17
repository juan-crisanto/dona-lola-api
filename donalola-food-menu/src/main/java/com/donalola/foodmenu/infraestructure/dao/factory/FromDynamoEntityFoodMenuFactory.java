package com.donalola.foodmenu.infraestructure.dao.factory;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import org.springframework.stereotype.Component;

@Component
public class FromDynamoEntityFoodMenuFactory implements FoodMenuFactory<FoodMenuDynamoEntity> {

    @Override
    public FoodMenu create(FoodMenuDynamoEntity foodMenuSource) {
        return FoodMenuEntityToDomainMapper.MAPPER.entityToDomain(foodMenuSource);
    }

    @Override
    public FoodMenuDynamoEntity create(FoodMenu foodMenu) {
        return FoodMenuEntityToDomainMapper.MAPPER.domainToEntity(foodMenu);
    }
}
