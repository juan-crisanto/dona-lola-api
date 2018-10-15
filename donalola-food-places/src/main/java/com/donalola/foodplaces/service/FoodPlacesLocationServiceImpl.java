package com.donalola.foodplaces.service;

import com.donalola.commons.GeoLocation;
import com.donalola.foodplaces.FoodPlace;
import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesResponseDto;
import com.donalola.foodplaces.factory.FoodPlaceFactory;
import com.donalola.util.localization.GeoLocationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodPlacesLocationServiceImpl implements FoodPlacesLocationService {

    private final GeoLocationUtil geoLocationUtil;
    private final FoodPlaceFactory foodPlaceFactory;

    public FoodPlacesLocationServiceImpl(GeoLocationUtil geoLocationUtil, FoodPlaceFactory foodPlaceFactory) {
        this.geoLocationUtil = geoLocationUtil;
        this.foodPlaceFactory = foodPlaceFactory;
    }

    @Override
    public FindNearbyFoodPlacesResponseDto getNearbyPlaces(FindNearbyFoodPlacesRequestDto request, List<FoodPlaceEntity> foodPlaceEntityList) {
        GeoLocation origin = new GeoLocation(request.getLatitude(), request.getLongitude());

        List<GeoLocation> places = foodPlaceEntityList.stream().map(f -> new GeoLocation(Double.parseDouble(f.getLatitude()), Double.parseDouble(f.getLongitude()))).collect(Collectors.toList());

        List<FoodPlace> result = new ArrayList<>(places.size());

        for (int i = 0; i < places.size(); i++) {
            double distance = geoLocationUtil.getDistanceBetween(origin, places.get(i));

            if (distance <= request.getRadius()) {
                result.add(this.foodPlaceFactory.create(foodPlaceEntityList.get(i)));
            }
        }

        FindNearbyFoodPlacesResponseDto responseDto = new FindNearbyFoodPlacesResponseDto();
        responseDto.setPlaces(result);

        return responseDto;
    }
}
