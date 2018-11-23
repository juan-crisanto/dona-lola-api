package com.donalola.foodmenu.application;

import com.donalola.core.rest.service.BaseController;
import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.FoodMenuManager;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/add")
    @ApiOperation(value = "Agrega un nuevo menú a un Local. Debe incluir los ítems del menú a ofrecer")
    public FoodMenuJson addMenuWithItems(@RequestBody FoodMenuJson foodMenu, Principal principal) {
        foodMenu.setFoodPlaceId(principal.getName());
        return this.foodMenuFactory.create(this.foodMenuManager.addMenuWithItems(this.foodMenuFactory.create(foodMenu)));
    }

    @PostMapping(value = "/{menuId}/item")
    @ApiOperation(value = "Agrega un nuevo item al menú de un Local.")
    public FoodMenuJson addItem(@PathVariable String menuId, @RequestBody List<FoodMenuJson.ItemJson> itemJsonList) {
        FoodMenuJson menuJson = new FoodMenuJson();
        menuJson.setId(menuId);
        menuJson.setItems(itemJsonList);
        FoodMenu foodMenu = this.foodMenuFactory.create(menuJson);
        return this.foodMenuFactory.create(this.foodMenuManager.addItemsToMenu(menuId, foodMenu.getItems()));
    }

    @GetMapping(value = "/local/{idLocal}")
    @ApiOperation(value = "Devuelve los menús del día del chef seleccionado")
    public List<FoodMenuJson> getByLocal(@PathVariable String idLocal) {
        List<FoodMenuJson> menuJsonList = new ArrayList<>();
        this.foodMenuManager
                .listTodayFoodPlaceMenus(idLocal)
                .forEach(foodMenu -> menuJsonList.add(this.foodMenuFactory.create(foodMenu)));
        return menuJsonList;
    }

    @GetMapping(value = "/today")
    @ApiOperation(value = "Devuelve los menús del día del chef seleccionado")
    public List<FoodMenuJson> getTodayMenu(Principal principal) {
        List<FoodMenuJson> menuJsonList = new ArrayList<>();
        this.foodMenuManager
                .listTodayFoodPlaceMenus(principal.getName())
                .forEach(foodMenu -> menuJsonList.add(this.foodMenuFactory.create(foodMenu)));
        return menuJsonList;
    }

}
