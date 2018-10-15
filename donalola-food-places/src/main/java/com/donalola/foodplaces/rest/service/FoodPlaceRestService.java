package com.donalola.foodplaces.rest.service;

import com.donalola.core.rest.service.BaseController;
import com.donalola.foodplaces.FoodPlaceManager;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesResponseDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/foodPlace")
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

}
