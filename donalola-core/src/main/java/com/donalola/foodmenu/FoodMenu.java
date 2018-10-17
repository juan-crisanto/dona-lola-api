package com.donalola.foodmenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
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
            Optional<Status> statusOptional = Arrays.asList(values()).stream()
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
    private List<ItemMenu> items;

    public FoodMenu() {
        this.createdDatetime = LocalDateTime.now();
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
        boolean stillItemsAvailable = this.getItems().parallelStream().anyMatch(itemMenu -> itemMenu.isAvailable());
        if (stillItemsAvailable) {
            throw new IllegalStateException("Items available yet");
        }
        this.status = this.status.spent();
    }

}
