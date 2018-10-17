package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import org.springframework.stereotype.Component;

@Component
public class FromJsonFoodMenuFactory implements FoodMenuFactory<FoodMenuJson> {

    @Override
    public FoodMenu create(final FoodMenuJson foodMenuSource) {
        return FoodMenuJsonToDomainMapper.MAPPER.jsonToDomain(foodMenuSource);
    }

    @Override
    public FoodMenuJson create(FoodMenu foodMenu) {
        return FoodMenuJsonToDomainMapper.MAPPER.domainToJson(foodMenu);
    }
}
