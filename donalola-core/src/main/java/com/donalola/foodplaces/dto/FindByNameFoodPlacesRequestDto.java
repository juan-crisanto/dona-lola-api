package com.donalola.foodplaces.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindByNameFoodPlacesRequestDto implements FoodPlaceRequestDto {

    private static final long serialVersionUID = -4792798894845068050L;

    @ApiModelProperty(required = true, example = "Lola")
    private String name;
}
