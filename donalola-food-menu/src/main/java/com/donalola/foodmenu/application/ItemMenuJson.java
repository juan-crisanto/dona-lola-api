package com.donalola.foodmenu.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
public class ItemMenuJson {

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
    private Integer quantityAvailable;
    @ApiModelProperty(value = "Número de pedidos sobre este plato", readOnly = true)
    private Integer takenOrders;
}
