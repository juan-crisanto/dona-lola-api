package com.donalola.foodmenu.domain;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;

public interface FoodMenuManager {

    FoodMenu add(FoodMenu foodMenu);

    FoodMenus getByFoodPlace(String foodPlaceId);

}
