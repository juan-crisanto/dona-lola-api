package com.donalola.foodplaces;


import com.donalola.core.Location;
import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import com.donalola.foodplaces.dto.FoodPlaceResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FoodPlaceDto implements Serializable, FoodPlaceRequestDto, FoodPlaceResponseDto {

    private static final long serialVersionUID = -7611843508795842619L;

    @ApiModelProperty(hidden = true)
    private String id;
    @ApiModelProperty(required = true, example = "Menu Piso 6")
    private String name;
    @ApiModelProperty(required = false, notes = "Imagen en Base 64")
    private String photoUrl;

    @ApiModelProperty(required = true)
    private Location location;

}
