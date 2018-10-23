package com.donalola.foodmenu;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


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
    private String image;

    public Integer getQuantityAvailable() {
        if (!Optional.ofNullable(this.quantityAvailable).isPresent()) {
            return this.quantityAvailable;
        }
        Integer takenOrders = Optional.ofNullable(this.getTakenOrders()).isPresent() ? this.getTakenOrders() : NumberUtils.INTEGER_ZERO;
        return (this.quantityAvailable - takenOrders);
    }

    public boolean isAvailable() {
        return (this.getQuantityAvailable() - this.getTakenOrders()) > 0;
    }

}
