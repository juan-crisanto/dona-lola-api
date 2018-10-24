package com.donalola.foodplaces.service;


import com.donalola.foodplaces.FoodPlace;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import com.donalola.foodplaces.dao.repository.FoodPlaceRepository;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SaveFoodPlaceServiceImpl implements FoodPlaceService<FindNearbyFoodPlacesRequestDto> {

    private final FoodPlaceRepository       foodPlaceRepository;
    private final FoodPlacesLocationService foodPlacesLocationService;

    public SaveFoodPlaceServiceImpl(FoodPlaceRepository foodPlaceRepository, FoodPlacesLocationService foodPlacesLocationService){
        this.foodPlaceRepository = foodPlaceRepository;
        this.foodPlacesLocationService = foodPlacesLocationService;
    }

    @Override
    public FoodPlaceResponseDto proceed(FindNearbyFoodPlacesRequestDto request){
        List<FoodPlaceEntity> foodPlaceEntityList = foodPlaceRepository.findAll();
        return foodPlacesLocationService.getNearbyPlaces(request, foodPlaceEntityList);
    }

    @Override
    public boolean supports(Class<?> supportedClass){
        return FoodPlace.class.isAssignableFrom(supportedClass);
    }
}
