package com.donalola.foodmenu.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FoodMenuJson {

    private String id;
    private String foodPlaceId;
    private LocalDateTime createdDatetime;
    private LocalDateTime finishedDatetime;
    private String name;
    private List<ItemMenuJson> items;

    boolean hasItems() {
        return CollectionUtils.isNotEmpty(this.getItems());
    }
}
