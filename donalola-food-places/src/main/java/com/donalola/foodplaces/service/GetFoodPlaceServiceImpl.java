package com.donalola.foodplaces.service;

import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import com.donalola.foodplaces.dto.GetFoodPlaceRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetFoodPlaceServiceImpl implements FoodPlaceService {

    @Override
    public FoodPlaceResponseDto proceed(FoodPlaceRequestDto foodPlaceRequestDto) {
        return null;
    }

    @Override
    public boolean supports(Class<?> supportedClass) {
        return GetFoodPlaceRequestDto.class.isAssignableFrom(supportedClass);
    }
}
