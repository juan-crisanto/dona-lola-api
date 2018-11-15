package com.donalola.chef.application;

import com.donalola.ChefID;
import com.donalola.chef.domain.Chef;
import com.donalola.chef.domain.ChefManager;
import com.donalola.core.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = "api/chef",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@Api(
        value = "Chefs",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class ChefRestService {

    private final ChefManager chefManager;

    public ChefRestService(ChefManager chefManager) {
        this.chefManager = chefManager;
    }

    @GetMapping(value = "/{chefId}")
    public ChefJson get(@PathVariable(value = "chefId") String chefId) {
        return ChefJson.of(this.chefManager.get(ChefID.of(chefId)));
    }

    @GetMapping(value = "/me")
    public ChefJson getMine(Principal principal) {
        return ChefJson.of(this.chefManager.getByUserId(principal.getName()));
    }

    @PostMapping(value = "/add")
    public ChefJson addChef(@RequestBody ChefJson chefJson, Principal principal) {
        Chef chef = Chef.builder()
                .Base64Image(chefJson.getImage())
                .ClosingOn(chefJson.getClosingSchedule())
                .OpeningOn(chefJson.getOpeningSchedule())
                .Location(chefJson.getLocation())
                .Owner(principal)
                .Name(chefJson.getName())
                .build();
        Chef addedChef = this.chefManager.add(chef);
        return ChefJson.of(addedChef);
    }

    @PostMapping(value = "/update")
    public ChefJson update(@RequestBody ChefJson chefJson, Principal principal) {
        Chef chef = Chef.builder()
                .Base64Image(chefJson.getImage())
                .ClosingOn(chefJson.getClosingSchedule())
                .OpeningOn(chefJson.getOpeningSchedule())
                .Location(chefJson.getLocation())
                .Owner(principal)
                .Name(chefJson.getName())
                .build();
        Chef addedChef = this.chefManager.add(chef);
        return ChefJson.of(addedChef);
    }

    @PostMapping(value = "/nearby")
    public List<ChefJson> findNearbyChefs(@RequestBody FindNearbyChefsRequest request) {
        Location location = new Location();
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        List<Chef> chefList = this.chefManager.listNearOf(location, request.getRadius());
        List<ChefJson> chefJsonList = new ArrayList<>(CollectionUtils.size(chefList));
        chefList.parallelStream().forEach(chef -> chefJsonList.add(ChefJson.of(chef)));
        return chefJsonList;
    }

    @PostMapping(value = "/by/name")
    public List<ChefJson> findChefsByName(@RequestBody FindChefsByNameRequest request) {
        List<Chef> chefList = this.chefManager.findByName(request.getName());
        List<ChefJson> chefJsonList = new ArrayList<>(CollectionUtils.size(chefList));
        chefList.parallelStream().forEach(chef -> chefJsonList.add(ChefJson.of(chef)));
        return chefJsonList;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class FindChefsByNameRequest {
        private String name;
    }


    @NoArgsConstructor
    @Setter
    @Getter
    public static class FindNearbyChefsRequest {
        private double latitude;
        private double longitude;
        private Integer radius;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Setter
    @Getter
    public static class ChefJson {

        private ChefJson(Chef chef) {
            this.id = chef.getId().toString();
            this.name = chef.getName();
            this.image = chef.getImage();
            this.openingSchedule = chef.getOpeningSchedule();
            this.closingSchedule = chef.getClosingSchedule();
            this.location = chef.getLocation();
        }

        private String id;
        private String name;
        private String image;
        private String openingSchedule;
        private String closingSchedule;
        private Location location;

        public static ChefJson of(Chef chef) {
            if (chef == null) {
                throw new IllegalArgumentException("Chef must no be null");
            }
            return new ChefJson(chef);
        }

    }

}
