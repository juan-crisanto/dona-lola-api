package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import org.springframework.stereotype.Component;

@Component
public class FromJsonFoodMenuFactory implements FoodMenuFactory<FoodMenuJson> {


    @Override
    public FoodMenu create(final FoodMenuJson foodMenuSource) {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setId(foodMenuSource.getId());
        foodMenu.setLocalDateTime(foodMenuSource.getLocalDateTime());
        foodMenu.setName(foodMenuSource.getName());
        foodMenu.setIdFoodPlace(foodMenuSource.getIdFoodPlace());
        return foodMenu;
    }

    @Override
    public FoodMenuJson create(FoodMenu foodMenu) {
        return new FoodMenuJson(foodMenu);
    }
}
