package com.donalola.foodplaces.rest.service;


import com.donalola.core.rest.service.BaseController;
import com.donalola.foodplaces.FoodPlaceDto;
import com.donalola.foodplaces.FoodPlaceManager;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesResponseDto;
import com.donalola.foodplaces.dto.FoodPlaceRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(
        value = "api/foodPlace",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@Api(
        value = "Locales de Comida",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class FoodPlaceRestService extends BaseController {

    private final FoodPlaceManager foodPlaceManager;

    public FoodPlaceRestService(FoodPlaceManager foodPlaceManager) {
        this.foodPlaceManager = foodPlaceManager;
    }

    @RequestMapping(value = "/listNearby", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Devuelve una lista de locales en el radio especificado(mts)")
    public FindNearbyFoodPlacesResponseDto findNearby(@Valid @RequestBody FindNearbyFoodPlacesRequestDto requestDto, BindingResult bindingResult) {
        return (FindNearbyFoodPlacesResponseDto) this.foodPlaceManager.proceed(requestDto);
    }

    @PostMapping
    @ApiOperation(value = "Registra nuevo local")
    public FoodPlaceRequestDto findNearby(@Valid @RequestBody FoodPlaceDto requestDto, BindingResult bindingResult) {
        return (FoodPlaceRequestDto) this.foodPlaceManager.proceed(requestDto);
    }
}