package com.donalola.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Location {

    @ApiModelProperty(required = true, example = "Lima")
    private String district;
    @ApiModelProperty(required = true, example = "Avenida")
    private String address;
    @ApiModelProperty(required = true, example = "-12.082988")
    private double latitude;
    @ApiModelProperty(required = true, example = "-12.082988")
    private double longitude;

}
