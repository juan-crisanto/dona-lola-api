package com.donalola.foodplaces.service;


import com.donalola.foodplaces.FoodPlaceDto;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import com.donalola.foodplaces.dao.repository.FoodPlaceRepository;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import com.donalola.foodplaces.factory.FoodPlaceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SaveFoodPlaceServiceImpl implements FoodPlaceService<FoodPlaceDto> {

    private final FoodPlaceRepository foodPlaceRepository;
    private final FoodPlaceFactory    foodPlaceFactory;

    public SaveFoodPlaceServiceImpl(FoodPlaceRepository foodPlaceRepository, FoodPlaceFactory foodPlaceFactory){
        this.foodPlaceRepository = foodPlaceRepository;
        this.foodPlaceFactory = foodPlaceFactory;
    }

    @Override
    public FoodPlaceResponseDto proceed(FoodPlaceDto request){
        FoodPlaceEntity placeEntity = foodPlaceRepository.save(foodPlaceFactory.fromDtoToEntity(request));
        return foodPlaceFactory.fromEntityToDto(placeEntity);
    }

    @Override
    public boolean supports(Class<?> supportedClass){
        return FoodPlaceDto.class.isAssignableFrom(supportedClass);
    }
}
