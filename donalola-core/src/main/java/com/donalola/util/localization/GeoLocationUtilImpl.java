package com.donalola.util.localization;

import com.donalola.commons.GeoLocation;
import org.springframework.stereotype.Component;

@Component
public class GeoLocationUtilImpl implements GeoLocationUtil {
    public double getDistanceBetween(GeoLocation from, GeoLocation to) {
        double theta = from.getLon() - to.getLon();
        double dist = Math.sin(deg2rad(from.getLat())) * Math.sin(deg2rad(to.getLat())) + Math.cos(deg2rad(from.getLat())) * Math.cos(deg2rad(to.getLat())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}