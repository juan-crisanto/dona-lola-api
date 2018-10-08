package com.donalola.foodplaces.dto;

import com.donalola.core.ValidationRegex;
import com.donalola.foodplaces.util.FoodPlacesErrorsCode;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class FindNearbyFoodPlacesRequestDto implements FoodPlaceRequestDto {

    @Pattern(regexp = ValidationRegex.CHORDS_REGEX, message = FoodPlacesErrorsCode.COORDINATES_ARE_NEEDED)
    private String latitude;
    @Pattern(regexp = ValidationRegex.CHORDS_REGEX, message = FoodPlacesErrorsCode.COORDINATES_ARE_NEEDED)
    private String longitude;
    @NotNull(message = FoodPlacesErrorsCode.METERS_AROUND_NECESARY)
    private Integer metersAround;

}
