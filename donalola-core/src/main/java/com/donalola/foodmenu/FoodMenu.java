package com.donalola.foodmenu;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FoodMenu {

    private String id;
    private String idFoodPlace;
    private LocalDate localDate;
    private List<ItemMenu> items;

}
