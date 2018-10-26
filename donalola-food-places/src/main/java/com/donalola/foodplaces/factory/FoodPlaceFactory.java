package com.donalola.foodplaces.factory;


import com.donalola.foodplaces.FoodPlaceDto;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;

public interface FoodPlaceFactory {

    FoodPlaceDto fromEntityToDto(FoodPlaceEntity foodPlaceEntity);

    FoodPlaceEntity fromDtoToEntity(FoodPlaceDto foodPlaceEntity);
}
