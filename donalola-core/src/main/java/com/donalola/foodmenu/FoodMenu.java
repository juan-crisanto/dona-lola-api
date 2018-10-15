package com.donalola.foodmenu;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FoodMenu {

    private String id;
    private String idFoodPlace;
    private LocalDateTime localDateTime;
    private String name;
    private List<ItemMenu> items;

    public boolean hasAnyItem() {
        return CollectionUtils.isNotEmpty(this.getItems());
    }

}
