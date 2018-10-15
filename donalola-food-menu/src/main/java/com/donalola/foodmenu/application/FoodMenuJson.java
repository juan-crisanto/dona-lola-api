package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FoodMenuJson {

    private String id;
    private String idFoodPlace;
    private LocalDateTime localDateTime;
    private String name;
    private List<ItemMenuJson> items;

    public FoodMenuJson() {
        this.localDateTime = LocalDateTime.now();
    }

    public FoodMenuJson(final FoodMenu foodMenu) {
        this.id = foodMenu.getId();
        this.idFoodPlace = foodMenu.getIdFoodPlace();
        this.localDateTime = foodMenu.getLocalDateTime();
        this.name = foodMenu.getName();
        if (foodMenu.hasAnyItem()) {
            this.items = new ArrayList<>(CollectionUtils.size(foodMenu.getItems()));
            foodMenu.getItems().forEach(item -> this.items.add(new ItemMenuJson(item)));
        }
    }

    boolean hasItems() {
        return CollectionUtils.isNotEmpty(this.getItems());
    }
}
