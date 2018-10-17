package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import org.springframework.stereotype.Component;

@Component
public class FromJsonFoodMenuFactory implements FoodMenuFactory<FoodMenuJson> {

    @Override
    public FoodMenu create(final FoodMenuJson foodMenuSource) {
        FoodMenu foodMenu = FoodMenuJsonToDomainMapper.MAPPER.jsonToDomain(foodMenuSource);
        return foodMenu;
    }

    @Override
    public FoodMenuJson create(FoodMenu foodMenu) {
        FoodMenuJson json = FoodMenuJsonToDomainMapper.MAPPER.domainToJson(foodMenu);
        return json;
    }
}
