package com.donalola.chef.domain;

import com.donalola.ChefID;
import com.donalola.core.Location;

import java.util.List;

public interface ChefManager {

    Chef add(Chef chef);

    List<Chef> listNearOf(Location location, Integer radius);

    Chef get(ChefID chefID);

    List<Chef> findByName(String name);
}
