package com.donalola.foodplaces.dto;

import com.donalola.foodplaces.FoodPlaceDto;
import lombok.Data;

@Data
public class GetFoodPlaceResponseDto implements FoodPlaceResponseDto {

    private FoodPlaceDto foodPlaceDto;

}
