package com.donalola.foodplaces.service;

import com.donalola.foodplaces.FoodPlace;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import com.donalola.foodplaces.dao.repository.FoodPlaceRepository;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesResponseDto;
import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import com.donalola.foodplaces.factory.FoodPlaceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FindNearbyFoodPlacesServiceImpl implements FoodPlaceService {

    private final FoodPlaceRepository foodPlaceRepository;
    private final FoodPlaceFactory foodPlaceFactory;

    public FindNearbyFoodPlacesServiceImpl(FoodPlaceRepository foodPlaceRepository, FoodPlaceFactory foodPlaceFactory) {
        this.foodPlaceRepository = foodPlaceRepository;
        this.foodPlaceFactory = foodPlaceFactory;
    }

    @Override
    public FoodPlaceResponseDto proceed(FoodPlaceRequestDto foodPlaceRequestDto) {
        FindNearbyFoodPlacesRequestDto findNearbyFoodPlacesRequestDdto = (FindNearbyFoodPlacesRequestDto) foodPlaceRequestDto;
        List<FoodPlaceEntity> foodPlaceEntityList = foodPlaceRepository.findAll();
        List<FoodPlace> foodPlaceList = new ArrayList<>(CollectionUtils.size(foodPlaceEntityList));
        foodPlaceEntityList.stream().forEach(foodPlaceEntity -> foodPlaceList.add(this.foodPlaceFactory.create((foodPlaceEntity))));
        FindNearbyFoodPlacesResponseDto responseDto = new FindNearbyFoodPlacesResponseDto();
        responseDto.setPlaces(foodPlaceList);
        return responseDto;
    }

    @Override
    public boolean supports(Class<?> supportedClass) {
        return FindNearbyFoodPlacesRequestDto.class.isAssignableFrom(supportedClass);
    }
}
