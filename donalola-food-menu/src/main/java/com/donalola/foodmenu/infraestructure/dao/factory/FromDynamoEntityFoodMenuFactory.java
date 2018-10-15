package com.donalola.foodmenu.infraestructure.dao.factory;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import org.springframework.stereotype.Component;

@Component
public class FromDynamoEntityFoodMenuFactory implements FoodMenuFactory<FoodMenuDynamoEntity> {

    @Override
    public FoodMenu create(FoodMenuDynamoEntity foodMenuSource) {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setIdFoodPlace(foodMenuSource.getIdFoodPlace());
        foodMenu.setId(foodMenuSource.getId());
        foodMenu.setLocalDateTime(foodMenuSource.getMenuDate());
        foodMenu.setName(foodMenuSource.getName());
        return foodMenu;
    }

    @Override
    public FoodMenuDynamoEntity create(FoodMenu foodMenu) {
        return new FoodMenuDynamoEntity(foodMenu);
    }
}
