package com.donalola.foodplaces.dto;

import com.donalola.foodplaces.FoodPlaceDto;
import lombok.Data;

import java.util.List;

@Data
public class FindNearbyFoodPlacesResponseDto implements FoodPlaceResponseDto {

    private List<FoodPlaceDto> places;
}
