package com.donalola.foodplaces.service;

import com.donalola.foodplaces.FoodPlaceDto;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import com.donalola.foodplaces.dao.repository.FoodPlaceRepository;
import com.donalola.foodplaces.dto.FindByNameFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FindByNameFoodPlacesResponseDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import com.donalola.foodplaces.factory.FoodPlaceFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FindFoodPlaceByNameServiceImpl implements FoodPlaceService<FindByNameFoodPlacesRequestDto> {

    private final FoodPlaceRepository foodPlaceRepository;
    private final FoodPlaceFactory foodPlaceFactory;

    public FindFoodPlaceByNameServiceImpl(FoodPlaceRepository foodPlaceRepository, FoodPlaceFactory foodPlaceFactory) {
        this.foodPlaceRepository = foodPlaceRepository;
        this.foodPlaceFactory = foodPlaceFactory;
    }

    @Override
    public FoodPlaceResponseDto proceed(FindByNameFoodPlacesRequestDto foodPlaceRequestDto) {
        FindByNameFoodPlacesResponseDto responseDto = new FindByNameFoodPlacesResponseDto();
        List<FoodPlaceEntity> entityList = this.foodPlaceRepository.findAllByNameContaining(foodPlaceRequestDto.getName());
        if (CollectionUtils.isEmpty(entityList)) {
            responseDto.setPlaces(Collections.emptyList());
            return responseDto;
        }
        List<FoodPlaceDto> foodPlaceDtoList = new ArrayList<>(CollectionUtils.size(entityList));
        entityList.forEach(foodPlaceEntity -> {
            foodPlaceDtoList.add(this.foodPlaceFactory.fromEntityToDto(foodPlaceEntity));
        });
        responseDto.setPlaces(foodPlaceDtoList);
        return responseDto;
    }

    @Override
    public boolean supports(Class<?> supportedClass) {
        return FindByNameFoodPlacesRequestDto.class.isAssignableFrom(supportedClass);
    }
}
