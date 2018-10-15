package com.donalola.foodplaces;

import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;

public interface FoodPlaceManager {

    FoodPlaceResponseDto proceed(FoodPlaceRequestDto foodPlaceRequestDto);

}
