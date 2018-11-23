package com.donalola.util.localization;

import com.donalola.Geolocated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

public class Geolocalization {

    /**
     * @param from
     * @param to
     * @return distance in meters between two points on a map
     */
    public static double getDistanceBetween(Geolocated from, Geolocated to) {
        double theta = from.getLongitude() - to.getLongitude();
        double dist = Math.sin(deg2rad(from.getLatitude())) * Math.sin(deg2rad(to.getLatitude())) + Math.cos(deg2rad(from.getLatitude())) * Math.cos(deg2rad(to.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = dist * 1000;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @RequiredArgsConstructor(staticName = "of")
    @Value(staticConstructor = "of")
    @Getter
    public static class ReferencePoint implements Geolocated {

        private double latitude;
        private double longitude;
    }

}
