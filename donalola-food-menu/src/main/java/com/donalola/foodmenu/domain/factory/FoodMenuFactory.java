package com.donalola.foodmenu.domain.factory;

import com.donalola.foodmenu.FoodMenu;

public interface FoodMenuFactory<T> {

    FoodMenu create(T foodMenuSource);

    T create(FoodMenu foodMenu);
}
