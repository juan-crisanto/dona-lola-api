package com.donalola.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class GeoLocation implements Serializable {
    private static final long serialVersionUID = 6707118105069302616L;

    private double lat;
    private double lon;

}
