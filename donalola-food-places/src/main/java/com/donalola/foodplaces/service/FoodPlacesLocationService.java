package com.donalola.foodplaces.service;

import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesResponseDto;

import java.util.List;

public interface FoodPlacesLocationService {
    FindNearbyFoodPlacesResponseDto getNearbyPlaces(FindNearbyFoodPlacesRequestDto request, List<FoodPlaceEntity> foodPlaceEntityList);
}
