package com.donalola.foodmenu.application;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ItemMenuJson {

    private String id;
    private String menuId;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantityAvailable;
    private Integer takenOrders;
}
