package com.donalola.foodplaces.dto;

import com.donalola.foodplaces.util.FoodPlacesErrorsCode;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetFoodPlaceRequestDto implements FoodPlaceRequestDto {

    @NotNull(message = FoodPlacesErrorsCode.FOOD_ID_NECESARY)
    private String id;

}
