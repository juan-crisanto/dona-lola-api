package com.donalola.chef.domain;

import com.donalola.ChefID;

import java.util.List;

public interface ChefRepository {

    Chef get(ChefID chefID);

    Chef save(Chef chef);

    Chef update(Chef chef);

    Chef getByUser(String userId);

    List<Chef> findByName(String name);

    List<Chef> findAll();


}
