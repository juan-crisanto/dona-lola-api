package com.donalola.chef.domain;

import com.donalola.Geolocated;
import com.donalola.core.Location;
import com.donalola.util.localization.Geolocalization;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChefFinder implements ChefService {

    private final ChefRepository chefRepository;

    public ChefFinder(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    @Override
    public List<Chef> findNearbyChefs(Location location, Integer radius) {
        List<Chef> chefList = chefRepository.findAll();
        Geolocated from = Geolocalization.ReferencePoint.of(location.getLatitude(), location.getLongitude());
        List<Chef> resultPoints = new ArrayList<>(CollectionUtils.size(chefList));
        for (Chef point : chefList) {
            if (Geolocalization.getDistanceBetween(from, point) <= radius) {
                resultPoints.add(point);
            }
        }
        return resultPoints;
    }
}
