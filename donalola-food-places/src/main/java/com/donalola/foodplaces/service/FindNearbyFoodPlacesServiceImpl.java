package com.donalola.foodplaces.service;


import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import com.donalola.foodplaces.dao.repository.FoodPlaceRepository;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FindNearbyFoodPlacesServiceImpl implements FoodPlaceService<FindNearbyFoodPlacesRequestDto> {

    private final FoodPlaceRepository       foodPlaceRepository;
    private final FoodPlacesLocationService foodPlacesLocationService;

    public FindNearbyFoodPlacesServiceImpl(FoodPlaceRepository foodPlaceRepository, FoodPlacesLocationService foodPlacesLocationService){
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
        return FindNearbyFoodPlacesRequestDto.class.isAssignableFrom(supportedClass);
    }
}
