package com.donalola.foodplaces.service;

import com.donalola.core.service.BaseService;
import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;

public interface FoodPlaceService extends BaseService {

    FoodPlaceResponseDto proceed(FoodPlaceRequestDto foodPlaceRequestDto);

}
