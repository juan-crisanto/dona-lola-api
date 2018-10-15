package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FoodMenuJson {

    private String id;
    private String idFoodPlace;
    private LocalDateTime localDateTime;
    private String name;

    public FoodMenuJson() {
        this.localDateTime = LocalDateTime.now();
    }

    public FoodMenuJson(final FoodMenu foodMenu) {
        this.id = foodMenu.getId();
        this.idFoodPlace = foodMenu.getIdFoodPlace();
        this.localDateTime = foodMenu.getLocalDateTime();
        this.name = foodMenu.getName();
    }
}
