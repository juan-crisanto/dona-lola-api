package com.donalola.foodplaces.factory;

import com.donalola.core.Location;
import com.donalola.foodplaces.FoodPlaceDto;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FoodPlaceFactoryImpl implements FoodPlaceFactory {

    @Override
    public FoodPlaceDto fromEntityToDto(FoodPlaceEntity foodPlaceEntity) {
        FoodPlaceDto foodPlaceDto = new FoodPlaceDto();
        setPlaceInfo(foodPlaceEntity, foodPlaceDto);
        setLocation(foodPlaceEntity, foodPlaceDto);
        return foodPlaceDto;
    }

    @Override
    public FoodPlaceEntity fromDtoToEntity(FoodPlaceDto  foodPlaceDto) {
        FoodPlaceEntity foodPlaceEntity = new FoodPlaceEntity();

        if(StringUtils.isNotEmpty(foodPlaceDto.getId()) ) {
            foodPlaceEntity.setId(Long.parseLong(foodPlaceDto.getId()));
        }
        foodPlaceEntity.setName(foodPlaceDto.getName());
        foodPlaceEntity.setPhotoUrl(foodPlaceDto.getPhotoUrl());

        foodPlaceEntity.setAddress(foodPlaceDto.getLocation().getAddress());
        foodPlaceEntity.setDistrict(foodPlaceDto.getLocation().getDistrict());
        foodPlaceEntity.setLatitude(String.valueOf(foodPlaceDto.getLocation().getLatitude()));
        foodPlaceEntity.setLongitude(String.valueOf(foodPlaceDto.getLocation().getLongitude()));

        return foodPlaceEntity;
    }

    private void setPlaceInfo(final FoodPlaceEntity foodPlaceEntity, FoodPlaceDto foodPlaceDto) {
        foodPlaceDto.setId(foodPlaceEntity.getId().toString());
        foodPlaceDto.setName(foodPlaceEntity.getName());
        foodPlaceDto.setPhotoUrl(foodPlaceEntity.getPhotoUrl());
    }

    private void setLocation(final FoodPlaceEntity foodPlaceEntity, FoodPlaceDto foodPlaceDto) {
        Location location = new Location();
        location.setAddress(foodPlaceEntity.getAddress());
        location.setDistrict(foodPlaceEntity.getDistrict());
        location.setLatitude(Double.parseDouble(foodPlaceEntity.getLatitude()));
        location.setLongitude(Double.parseDouble(foodPlaceEntity.getLongitude()));
        foodPlaceDto.setLocation(location);
    }
}
