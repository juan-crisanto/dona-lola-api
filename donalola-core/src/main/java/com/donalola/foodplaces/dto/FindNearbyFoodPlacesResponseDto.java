package com.donalola.foodplaces.dto;

import com.donalola.foodplaces.FoodPlace;
import lombok.Data;

import java.util.List;

@Data
public class FindNearbyFoodPlacesResponseDto implements FoodPlaceResponseDto {

    private List<FoodPlace> places;
}
