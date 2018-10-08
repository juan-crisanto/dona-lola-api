package com.donalola.core;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Location {

    private String district;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
