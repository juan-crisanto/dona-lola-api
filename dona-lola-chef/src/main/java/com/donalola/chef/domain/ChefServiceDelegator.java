package com.donalola.chef.domain;

import com.donalola.ChefID;
import com.donalola.core.Location;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChefServiceDelegator implements ChefManager {

    private final ChefRepository chefRepository;
    private final ChefService chefService;

    public ChefServiceDelegator(ChefRepository chefRepository, ChefService chefService) {
        this.chefRepository = chefRepository;
        this.chefService = chefService;
    }

    @Override
    public Chef add(Chef chef) {
        return this.chefRepository.save(chef);
    }

    @Override
    public List<Chef> listNearOf(Location location, Integer radius) {
        return this.chefService.findNearbyChefs(location, radius);
    }

    @Override
    public Chef get(ChefID chefID) {
        return this.chefRepository.get(chefID);
    }

    @Override
    public List<Chef> findByName(String name) {
        return this.chefRepository.findByName(name);
    }

}
