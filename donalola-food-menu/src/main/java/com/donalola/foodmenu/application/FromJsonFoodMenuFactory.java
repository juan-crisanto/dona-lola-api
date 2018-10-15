package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.domain.factory.ItemMenuFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FromJsonFoodMenuFactory implements FoodMenuFactory<FoodMenuJson> {

    private final ItemMenuFactory<ItemMenuJson> itemMenuJsonItemMenuFactory;

    public FromJsonFoodMenuFactory(ItemMenuFactory<ItemMenuJson> itemMenuJsonItemMenuFactory) {
        this.itemMenuJsonItemMenuFactory = itemMenuJsonItemMenuFactory;
    }

    @Override
    public FoodMenu create(final FoodMenuJson foodMenuSource) {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setId(foodMenuSource.getId());
        foodMenu.setLocalDateTime(foodMenuSource.getLocalDateTime());
        foodMenu.setName(foodMenuSource.getName());
        foodMenu.setIdFoodPlace(foodMenuSource.getIdFoodPlace());
        if (foodMenuSource.hasItems()) {
            List<ItemMenu> itemMenuList = new ArrayList<>(foodMenuSource.getItems().size());
            foodMenuSource.getItems().forEach(itemMenuJson -> itemMenuList.add(this.itemMenuJsonItemMenuFactory.create(itemMenuJson)));
            foodMenu.setItems(itemMenuList);
        }
        return foodMenu;
    }

    @Override
    public FoodMenuJson create(FoodMenu foodMenu) {
        return new FoodMenuJson(foodMenu);
    }
}
