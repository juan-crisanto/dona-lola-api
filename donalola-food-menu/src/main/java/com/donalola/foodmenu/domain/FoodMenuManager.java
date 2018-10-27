package com.donalola.foodmenu.domain;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;

import java.util.List;

public interface FoodMenuManager {

    FoodMenu sellItem(String foodMenuId, String itemId, Integer quantity);

    FoodMenu addMenuWithItems(FoodMenu foodMenu);

    FoodMenu addItemsToMenu(String foodMenuId, List<FoodMenu.Item> itemList);

    FoodMenu get(String menuId);

    FoodMenus getByFoodPlace(String foodPlaceId);

    FoodMenus listTodayFoodPlaceMenus(String foodPlaceId);

}
