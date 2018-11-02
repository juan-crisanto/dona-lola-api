package com.donalola.orders;

import com.donalola.*;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -2667895201434490624L;

    public enum Status {
        OPEN {
            @Override
            public Status setReady() {
                return READY_TO_DELIVER;
            }

            @Override
            public boolean onFinalStaus() {
                return false;
            }

            @Override
            public Status setDelivered() {
                throw new IllegalStateException(String.format("The order is not %s yet", READY_TO_DELIVER));
            }
        }, REJECTED {

        }, CANCELED {

        }, READY_TO_DELIVER {
            @Override
            public Status setReady() {
                return this;
            }

            @Override
            public boolean onFinalStaus() {
                return false;
            }

            @Override
            public Status setDelivered() {
                return DELIVERED;
            }
        }, DELIVERED {
            @Override
            public Status setDelivered() {
                return this;
            }
        };

        public Status setReady() {
            throw new IllegalStateException(String.format("The Order is already %s", this));
        }

        public boolean onFinalStaus() {
            return true;
        }

        public Status setDelivered() {
            throw new IllegalStateException(String.format("The Order is already %s", this));
        }

    }

    public enum PaymentMethod {
        CASH, CREDIT_CARD
    }

    private String id;
    private LocalDateTime createdDatetime;
    private LocalDateTime modifiedDatetime;
    private FoodPlaceID foodPlaceID;
    private CustomerID customerID;
    private CustomerDetails customerDetails;
    private Status status;
    private PaymentMethod paymentMethod;
    private List<Item> items;
    @Setter(AccessLevel.PRIVATE)
    private BigDecimal totalPrice;
    private String comment;

    public Order() {
        this.status = Status.OPEN;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.totalPrice = this.calculateTotalPrice();
    }

    public boolean isAvailable() {
        return !this.getStatus().onFinalStaus();
    }

    public void setReady() {
        this.status = this.status.setReady();
    }

    public void deliver() {
        this.status = this.status.setDelivered();
    }

    private BigDecimal calculateTotalPrice() {
        if (CollectionUtils.isEmpty(this.getItems())) {
            throw new IllegalStateException("No se han especificado ítems para la orden");
        }
        BigDecimal total = this.items
                .parallelStream()
                .map(Item::getTotalPrice)
                .reduce(BigDecimal::add)
                .get();
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("La orden no posee precios definidos");
        }
        return total;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Item implements Serializable {

        private static final long serialVersionUID = -3778631594325389133L;

        private FoodMenuID foodMenuID;
        private ItemMenuID itemMenuID;
        private ItemMenuDetails itemMenuDetails;
        private Integer quantity;

        public BigDecimal getTotalPrice() {
            return this.getItemMenuDetails().getPrice().multiply(NumberUtils.createBigDecimal(this.getQuantity().toString()));
        }
    }

}
