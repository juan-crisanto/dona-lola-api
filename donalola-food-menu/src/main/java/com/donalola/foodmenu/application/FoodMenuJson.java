package com.donalola.foodmenu.application;

import com.donalola.util.JsonFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
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
    private List<ItemJson> items;

    boolean hasItems() {
        return CollectionUtils.isNotEmpty(this.getItems());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Setter
    @Getter
    public static class ItemJson {

        @ApiModelProperty(value = "Identificador del plato del menú", readOnly = true)
        private String id;
        @ApiModelProperty(value = "Identificador del menú al que pertenece el plato", readOnly = true)
        private String menuId;
        @ApiModelProperty(value = "Nombre del plato", required = true)
        @NotNull
        private String name;
        @ApiModelProperty(value = "Descripción del plato")
        private String description;
        @ApiModelProperty(value = "Precio de venta del plato")
        @NotNull
        private BigDecimal price;
        @ApiModelProperty(value = "Cantidad que será ofertada para este plato")
        @NotNull
        private Integer stock;
        @ApiModelProperty(value = "Cantidad disponible en stock", readOnly = true)
        private Integer quantityAvailable;
        @ApiModelProperty(value = "Número de pedidos sobre este plato", readOnly = true)
        private Integer takenOrders;
        @ApiModelProperty(value = "Imagen base64 para este plato", required = true)
        private String image;

    }
}
