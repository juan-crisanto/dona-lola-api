package com.donalola.foodmenu.application;

import com.donalola.foodmenu.ItemMenu;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ItemMenuJson {

    private String idMenu;
    private String id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantityAvailable;

    public ItemMenuJson(ItemMenu itemMenu) {
        this.idMenu = itemMenu.getIdMenu();
        this.id = itemMenu.getId();
        this.name = itemMenu.getName();
        this.description = itemMenu.getDescription();
        this.price = itemMenu.getPrice();
        this.quantityAvailable = itemMenu.getQuantityAvailable();
    }
}
