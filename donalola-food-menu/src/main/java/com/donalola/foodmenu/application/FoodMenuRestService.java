package com.donalola.foodmenu.application;

import com.donalola.core.rest.service.BaseController;
import com.donalola.foodmenu.domain.FoodMenuManager;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = "/api/food-menu",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@Api(
        value = "Maneja los servicios sobre menús",
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

    @PutMapping
    @ApiOperation(value = "Agrega un nuevo menú a un Local. Debe incluir los ítems del menú a ofrecer")
    public FoodMenuJson addMenuWithItems(@RequestBody FoodMenuJson foodMenu, BindingResult bindingResult, Principal principal) {
        if (log.isDebugEnabled()) {
            log.debug("Add for principal: " + principal);
        }
        return this.foodMenuFactory.create(this.foodMenuManager.add(this.foodMenuFactory.create(foodMenu)));
    }

    @GetMapping(value = "/local/{idLocal}")
    @ApiOperation(value = "Devuelve los menús del día del local indicado")
    public List<FoodMenuJson> getByLocal(@PathVariable String idLocal) {
        List<FoodMenuJson> menuJsonList = new ArrayList<>();
        this.foodMenuManager.listTodayFoodPlaceMenus(idLocal).forEach(foodMenu -> menuJsonList.add(this.foodMenuFactory.create(foodMenu)));
        return menuJsonList;
    }

}
