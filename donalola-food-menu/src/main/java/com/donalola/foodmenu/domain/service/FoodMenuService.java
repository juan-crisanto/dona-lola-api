package com.donalola.foodmenu.domain.service;

import com.donalola.foodmenu.FoodMenu;

public interface FoodMenuService {

    FoodMenu sellItem(String foodMenuId, String itemId, Integer quantity);

}
