package com.donalola.chef.domain;

import com.donalola.ChefID;

import java.util.List;

public interface ChefRepository {

    Chef get(ChefID chefID);

    Chef save(Chef chef);

    List<Chef> findByName(String name);

    List<Chef> findAll();


}
