package com.donalola;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class ItemMenuDetails {

    private String name;
    private String description;
    private BigDecimal price;
}
