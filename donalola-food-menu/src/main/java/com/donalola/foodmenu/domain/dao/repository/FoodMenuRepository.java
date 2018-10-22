package com.donalola.foodmenu.domain.dao.repository;

import com.donalola.foodmenu.FoodMenu;

public interface FoodMenuRepository {

    FoodMenu addMenuWithItems(FoodMenu foodMenu);

    FoodMenu addItemsToMenu(FoodMenu foodMenu);

}
