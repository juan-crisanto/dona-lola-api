package com.donalola.foodmenu.domain;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;

public interface FoodMenuManager {

    FoodMenu addMenuWithItems(FoodMenu foodMenu);

    FoodMenu addItemsToMenu(FoodMenu foodMenu);

    FoodMenus getByFoodPlace(String foodPlaceId);

    FoodMenus listTodayFoodPlaceMenus(String foodPlaceId);

}
