package com.donalola.foodmenu.application;

import com.donalola.foodmenu.domain.FoodMenuManager;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(
        value = "/api/excluded/food-menu",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class FoodMenuTestRestService {

    private final FoodMenuManager foodMenuManager;
    private final FoodMenuFactory<FoodMenuJson> foodMenuFactory;

    public FoodMenuTestRestService(FoodMenuManager foodMenuManager, FoodMenuFactory<FoodMenuJson> foodMenuFactory) {
        this.foodMenuManager = foodMenuManager;
        this.foodMenuFactory = foodMenuFactory;
    }

    @PostMapping(value = "/add")
    public FoodMenuJson addMenuWithItems(@RequestBody FoodMenuJson foodMenu) {
        return this.foodMenuFactory.create(this.foodMenuManager.addMenuWithItems(this.foodMenuFactory.create(foodMenu)));
    }
}
