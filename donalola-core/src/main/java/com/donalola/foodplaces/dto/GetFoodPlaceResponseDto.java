package com.donalola.foodplaces.dto;

import com.donalola.foodplaces.FoodPlace;
import lombok.Data;

@Data
public class GetFoodPlaceResponseDto implements FoodPlaceResponseDto {

    private FoodPlace foodPlace;

}
