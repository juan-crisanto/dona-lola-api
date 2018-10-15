package com.donalola.foodmenu.application;

import com.donalola.core.rest.service.BaseController;
import com.donalola.foodmenu.domain.FoodMenuManager;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(
        value = "/api/food-menu",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class FoodMenuRestService extends BaseController {

    private final FoodMenuManager foodMenuManager;
    private final FoodMenuFactory<FoodMenuJson> foodMenuFactory;

    public FoodMenuRestService(FoodMenuManager foodMenuManager, FoodMenuFactory<FoodMenuJson> foodMenuFactory) {
        this.foodMenuManager = foodMenuManager;
        this.foodMenuFactory = foodMenuFactory;
    }

    @PostMapping(value = "/add")
    public FoodMenuJson addMenuWithItems(@RequestBody FoodMenuJson foodMenu, BindingResult bindingResult, Principal principal) {
        if (log.isDebugEnabled()) {
            log.debug("Add for principal: " + principal);
        }
        return this.foodMenuFactory.create(this.foodMenuManager.add(this.foodMenuFactory.create(foodMenu)));
    }


}
