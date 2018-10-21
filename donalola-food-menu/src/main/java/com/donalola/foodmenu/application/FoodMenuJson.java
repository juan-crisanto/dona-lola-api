package com.donalola.foodmenu.application;

import com.donalola.util.JsonFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FoodMenuJson {

    @ApiModelProperty(value = "Identificador único de menú", readOnly = true)
    private String id;
    @ApiModelProperty(value = "Id del Local de comida", required = true)
    private String foodPlaceId;
    @ApiModelProperty(value = "Fecha y hora de creación del menú", readOnly = true)
    @JsonFormat(locale = JsonFormatConstant.DEFAULT_LOCALE_VALUE, timezone = JsonFormatConstant.DEFAULT_TIMEZONE)
    private LocalDateTime createdDatetime;
    @ApiModelProperty(value = "Fecha y hora de cierre del menú", readOnly = true)
    private LocalDateTime finishedDatetime;
    private String name;
    @ApiModelProperty(value = "Items del menú. Son los platos que los conforman")
    private List<ItemMenuJson> items;

    boolean hasItems() {
        return CollectionUtils.isNotEmpty(this.getItems());
    }
}
