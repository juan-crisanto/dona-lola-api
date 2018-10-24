package com.donalola.foodplaces.factory;

import com.donalola.core.Location;
import com.donalola.foodplaces.FoodPlace;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class FoodPlaceFactoryImpl implements FoodPlaceFactory {

    @Override
    public FoodPlace create(FoodPlaceEntity foodPlaceEntity) {
        FoodPlace foodPlace = new FoodPlace();
        setPlaceInfo(foodPlaceEntity, foodPlace);
        setLocation(foodPlaceEntity, foodPlace);
        return foodPlace;
    }

    private void setPlaceInfo(final FoodPlaceEntity foodPlaceEntity, FoodPlace foodPlace) {
        foodPlace.setId(foodPlaceEntity.getId().toString());
        foodPlace.setName(foodPlaceEntity.getName());
        foodPlace.setPhotoUrl(foodPlaceEntity.getPhotoUrl());
    }

    private void setLocation(final FoodPlaceEntity foodPlaceEntity, FoodPlace foodPlace) {
        Location location = new Location();
        location.setAddress(foodPlaceEntity.getAddress());
        location.setDistrict(foodPlaceEntity.getDistrict());
        location.setLatitude(Long.parseLong(foodPlaceEntity.getLatitude()));
        location.setLongitude(Long.parseLong(foodPlaceEntity.getLongitude()));
        foodPlace.setLocation(location);
    }
}
