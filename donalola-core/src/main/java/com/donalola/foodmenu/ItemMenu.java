package com.donalola.foodmenu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
public class ItemMenu {

    private String id;
    private String menuId;
    private LocalDateTime createdTime;
    private String name;
    private String description;
    private Integer quantityAvailable;
    private BigDecimal price;
    private Integer takenOrders;

    public Integer getAvailable() {
        return (this.getQuantityAvailable() - this.getTakenOrders());
    }

    public boolean isAvailable() {
        return (this.getQuantityAvailable() - this.getTakenOrders()) > 0;
    }

}
