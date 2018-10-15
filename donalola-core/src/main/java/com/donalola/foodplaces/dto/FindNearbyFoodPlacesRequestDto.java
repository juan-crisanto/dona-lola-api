package com.donalola.foodplaces.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FindNearbyFoodPlacesRequestDto implements FoodPlaceRequestDto {

    private static final long serialVersionUID = -8353473616261973073L;
    //    @Pattern(regexp = ValidationRegex.CHORDS_REGEX, message = FoodPlacesErrorsCode.COORDINATES_ARE_NEEDED)
    @ApiModelProperty(required = true, example = "-12.082988")
    private double latitude;
    //    @Pattern(regexp = ValidationRegex.CHORDS_REGEX, message = FoodPlacesErrorsCode.COORDINATES_ARE_NEEDED)
    @ApiModelProperty(required = true, example = "-77.0235867")
    private double longitude;
    //    @NotNull(message = FoodPlacesErrorsCode.METERS_AROUND_NECESARY)
    @ApiModelProperty(required = true, example = "1000", notes = "Valor en metros")
    private int radius;

}
