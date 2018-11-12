package com.donalola.chef.application;

import com.donalola.chef.domain.Chef;
import com.donalola.chef.domain.ChefManager;
import com.donalola.core.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
