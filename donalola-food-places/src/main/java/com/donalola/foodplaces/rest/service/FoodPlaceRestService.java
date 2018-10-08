package com.donalola.foodplaces.rest.service;

import com.donalola.foodplaces.dto.FindNearbyFoodPlacesRequestDto;
import com.donalola.foodplaces.dto.FindNearbyFoodPlacesResponseDto;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("foodPlace")
public class FoodPlaceRestService {

    @RequestMapping(value = "/nearby", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public FindNearbyFoodPlacesResponseDto findNearby(@Valid @RequestBody FindNearbyFoodPlacesRequestDto requestDto, BindingResult bindingResult) {
        return new FindNearbyFoodPlacesResponseDto();
    }

}
