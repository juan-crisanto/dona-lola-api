package com.donalola.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -2667895201434490624L;

    public enum Status {

        OPEN, REJECTED, CANCELED, DELIVERED

    }

    public enum PaymentMethod {
        CASH, CREDIT_CARD
    }

    private String id;
    private LocalDateTime createdDateTime;
    private String customerId;
    private String customerName;
    private String foodPlaceId;
    private Status status;
    private PaymentMethod paymentMethod;
    private List<OrderItem> items;
    private BigDecimal totalPrice;

    public Order() {
        this.status = Status.OPEN;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        this.totalPrice = this.calculateTotalPrice();
    }

    private BigDecimal calculateTotalPrice() {
        if (CollectionUtils.isEmpty(this.getItems())) {
            throw new IllegalStateException("No se han especificado ítems para la orden");
        }
        BigDecimal total = this.items
                .parallelStream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal::add)
                .get();
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("La orden no posee precios definidos");
        }
        return total;
    }

}
