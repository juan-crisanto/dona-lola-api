package com.donalola.foodmenu;

import com.donalola.core.SimpleIterable;

import java.time.LocalDate;
import java.util.Iterator;

public class JustToIterateFoodMenus extends SimpleIterable<FoodMenu> implements FoodMenus {

    public JustToIterateFoodMenus(Iterator<FoodMenu> iterator) {
        super(iterator);
    }

    @Override
    public FoodMenus listByFoodPlace(String idFoodPlace) {
        throw new UnsupportedOperationException("Just for iterate");
    }

    @Override
    public FoodMenus listByDate(LocalDate localDate) {
        throw new UnsupportedOperationException("Just for iterate");
    }

    @Override
    public FoodMenus listTodayFoodPlaceMenus(String foodPlaceId) {
        throw new UnsupportedOperationException("Just for iterate");
    }
}
