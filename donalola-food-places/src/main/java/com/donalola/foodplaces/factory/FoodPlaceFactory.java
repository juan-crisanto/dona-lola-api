package com.donalola.foodplaces.factory;

import com.donalola.foodplaces.FoodPlace;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;

public interface FoodPlaceFactory {

    FoodPlace create(FoodPlaceEntity foodPlaceEntity);

}
