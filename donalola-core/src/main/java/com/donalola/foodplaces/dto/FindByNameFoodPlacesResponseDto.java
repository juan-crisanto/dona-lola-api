package com.donalola.foodplaces.dto;

import com.donalola.foodplaces.FoodPlaceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FindByNameFoodPlacesResponseDto implements FoodPlaceResponseDto {

    private List<FoodPlaceDto> places;

}
