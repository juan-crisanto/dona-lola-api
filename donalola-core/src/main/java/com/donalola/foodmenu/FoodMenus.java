package com.donalola.foodmenu;

import java.time.LocalDate;

public interface FoodMenus extends Iterable<FoodMenu> {

    FoodMenus listByFoodPlace(String idFoodPlace);

    FoodMenus listByDate(LocalDate localDate);

    FoodMenus listTodayFoodPlaceMenus(String foodPlaceId);
}
