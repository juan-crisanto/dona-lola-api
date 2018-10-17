package com.donalola.foodmenu;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemMenu {

    private String idMenu;
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityAvailable;
    private Integer takenOrders;

    public Integer getAvailable() {
        return (this.getQuantityAvailable() - this.getTakenOrders());
    }

    public boolean isAvailable() {
        return (this.getQuantityAvailable() - this.getTakenOrders()) > 0;
    }

}
