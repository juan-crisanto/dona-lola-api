package com.donalola.orders;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -3778631594325389133L;

    private String id;
    private LocalDateTime createdDateTime;
    private String menuId;
    private String menuItemId;
    private String description;
    private String quantity;
    private String comment;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

}
