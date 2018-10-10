package com.donalola.foodplaces.dto;

import com.donalola.core.ValidationRegex;
import com.donalola.foodplaces.util.FoodPlacesErrorsCode;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class FindNearbyFoodPlacesRequestDto implements FoodPlaceRequestDto {

    private static final long serialVersionUID = -8353473616261973073L;
    @Pattern(regexp = ValidationRegex.CHORDS_REGEX, message = FoodPlacesErrorsCode.COORDINATES_ARE_NEEDED)
    private double latitude;
    @Pattern(regexp = ValidationRegex.CHORDS_REGEX, message = FoodPlacesErrorsCode.COORDINATES_ARE_NEEDED)
    private double longitude;
    @NotNull(message = FoodPlacesErrorsCode.METERS_AROUND_NECESARY)
    private int radius;

}
