package com.donalola.foodplaces.service;


import com.donalola.core.service.BaseService;
import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;

public interface FoodPlaceService<T extends FoodPlaceRequestDto> extends BaseService {

    FoodPlaceResponseDto proceed(T foodPlaceRequestDto);

}
