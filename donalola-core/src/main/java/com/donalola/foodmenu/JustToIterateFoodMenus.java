package com.donalola.foodmenu;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

public class JustToIterateFoodMenus implements FoodMenus {

    private Iterator<FoodMenu> foodMenuIterator;

    public JustToIterateFoodMenus(Iterator<FoodMenu> iterator) {
        if (!Optional.ofNullable(iterator).isPresent()) {
            throw new IllegalArgumentException("Iterator must not be null");
        }
        this.foodMenuIterator = iterator;
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
    public Iterator<FoodMenu> iterator() {
        return this.foodMenuIterator;
    }
}
