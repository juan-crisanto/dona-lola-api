package com.donalola.chef.domain;

import com.donalola.ChefID;
import com.donalola.core.Location;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChefServiceDelegator implements ChefManager {

    private final ChefRepository chefRepository;

    public ChefServiceDelegator(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    @Override
    public Chef add(Chef chef) {
        return this.chefRepository.save(chef);
    }

    @Override
    public List<Chef> listNearOf(Location location) {
        return null;
    }

    @Override
    public Chef get(ChefID chefID) {
        return null;
    }

    @Override
    public List<Chef> findByName(String name) {
        return null;
    }
}
