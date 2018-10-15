package com.donalola.foodplaces.util;

public class FoodPlacesErrorsCode {

    private FoodPlacesErrorsCode() {
    }

    public static final String FOOD_PLACE_ERROR_CODE_PREFIX = "FoodPlace.";
    public static final String METERS_AROUND_NECESARY = FOOD_PLACE_ERROR_CODE_PREFIX + "MetersAroundMustBePresent";
    public static final String COORDINATES_ARE_NEEDED = FOOD_PLACE_ERROR_CODE_PREFIX + "CoordinatesAreNeeded";
    public static final String FOOD_ID_NECESARY = FOOD_PLACE_ERROR_CODE_PREFIX + "FoodPlaceIdIsNecesary";
}
