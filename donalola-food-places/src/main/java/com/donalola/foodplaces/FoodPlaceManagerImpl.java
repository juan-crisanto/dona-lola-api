package com.donalola.foodplaces;

import com.donalola.core.manager.BaseAbstractManager;
import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import com.donalola.foodplaces.service.FoodPlaceService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodPlaceManagerImpl extends BaseAbstractManager<FoodPlaceService> implements FoodPlaceManager {

    @Getter
    private final List<FoodPlaceService> serviceList;

    public FoodPlaceManagerImpl(List<FoodPlaceService> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public FoodPlaceResponseDto proceed(FoodPlaceRequestDto foodPlaceRequestDto) {
        FoodPlaceService foodPlaceService = getService(foodPlaceRequestDto.getClass());
        return foodPlaceService.proceed(foodPlaceRequestDto);
    }

}
