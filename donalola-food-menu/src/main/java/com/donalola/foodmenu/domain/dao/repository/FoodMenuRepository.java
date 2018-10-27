package com.donalola.foodmenu.domain.dao.repository;

import com.donalola.foodmenu.FoodMenu;

import java.util.List;

public interface FoodMenuRepository {

    void update(FoodMenu foodMenu);

    FoodMenu addMenuWithItems(FoodMenu foodMenu);

    FoodMenu addItemsToMenu(String menuId, List<FoodMenu.Item> itemList);

    FoodMenu get(String menuId);

    FoodMenu updateItems(String menuId, List<FoodMenu.Item> itemList);

}
