package com.donalola.chef.domain;

import com.donalola.core.Location;

import java.util.List;

public interface ChefService {

    List<Chef> findNearbyChefs(Location location, Integer radius);

}
