package com.donalola.foodmenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class FoodMenu implements Serializable {

    private static final long serialVersionUID = 866445157546391021L;

    public enum Status {

        OPEN {
            @Override
            protected Status close() {
                return CLOSED;
            }

            @Override
            protected Status spent() {
                return SOLD_OUT;
            }
        },
        SOLD_OUT {
            @Override
            protected Status spent() {
                return this;
            }
        },
        CLOSED {
            @Override
            protected Status close() {
                return this;
            }
        };

        private Status transitionNotAllowed(String action) {
            throw new IllegalStateException(String.format("Cannot %s already %s issue!", action, this));
        }

        protected Status spent() {
            return transitionNotAllowed("spent");
        }

        protected Status close() {
            return transitionNotAllowed("close");
        }

        public static Status parse(final String status) {
            Optional<Status> statusOptional = Arrays.stream(values())
                    .filter(s -> StringUtils.equals(s.name(), status))
                    .findFirst();
            if (!statusOptional.isPresent()) {
                throw new IllegalArgumentException("No valid status" + status);
            }
            return statusOptional.get();
        }

    }

    private String id;
    private String foodPlaceId;
    private Status status;
    private LocalDateTime createdDatetime;
    private LocalDateTime finishedDatetime;
    private String name;
    private List<Item> items;

    public FoodMenu() {
        this.status = Status.OPEN;
    }

    public boolean hasAnyItem() {
        return CollectionUtils.isNotEmpty(this.getItems());
    }

    public boolean isAvailable() {
        return this.status == Status.OPEN;
    }

    public void close() {
        this.status = this.status.close();
    }

    public void spent() {
        if (CollectionUtils.isEmpty(this.getItems())) {
            throw new IllegalStateException("Has no items to sell");
        }
        boolean stillItemsAvailable = this.getItems().parallelStream().anyMatch(Item::isAvailable);
        if (stillItemsAvailable) {
            throw new IllegalStateException("Items available yet");
        }
        this.status = this.status.spent();
    }

    public void takeItem(final String itemId, final Integer quantity) {
        List<Item> updatedItems = new ArrayList<>(CollectionUtils.size(this.getItems()));
        int i = 0;
        for (Item item : this.getItems()) {
            if (StringUtils.equals(item.getId(), itemId)) {
                item.take(quantity);
                this.getItems().set(i, item);
                break;
            }
            i++;
        }
        this.setItems(updatedItems);
    }

    public Item getItem(String itemId) {
        Optional<Item> item = this.getItems().parallelStream().filter(i -> StringUtils.equals(i.getId(), itemId)).findFirst();
        if (!item.isPresent()) {
            throw new ItemNotFoundOnMenuException("No se ha encontrado un Item con ID: " + itemId);
        }
        return item.get();
    }

    public boolean isItemAvailable(final String itemId, final Integer quantity) {
        Optional<Item> item = this.getItems().parallelStream().filter(i -> StringUtils.equals(i.getId(), itemId)).findFirst();
        if (!item.isPresent()) {
            throw new ItemNotFoundOnMenuException("No se ha encontrado un Item con ID: " + itemId);
        }
        return item.get().hasEnough(quantity);
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Item {

        private String id;

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

        public boolean hasEnough(Integer quantity) {
            return quantity > this.getQuantityAvailable();
        }

        public void take(Integer quantity) {
            if (!hasEnough(quantity)) {
                throw new ItemMenuNotAvailable(this.id);
            }
            this.takenOrders = this.takenOrders + quantity;
        }

    }

    public static class ItemNotFoundOnMenuException extends RuntimeException {

        private static final long serialVersionUID = 2463626542100834605L;

        public ItemNotFoundOnMenuException(String message) {
            super(message);
        }
    }

    public static class ItemMenuNotAvailable extends RuntimeException {

        private static final long serialVersionUID = 4828411084944281803L;

        public ItemMenuNotAvailable(String itemId) {
            super(String.format("Item del menú con ID %s no disponible", itemId));
        }
    }

}
