package com.donalola.commons;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeoLocation implements Serializable {
    private static final long serialVersionUID = 6707118105069302616L;

    private double lat;
    private double lon;

    public GeoLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
