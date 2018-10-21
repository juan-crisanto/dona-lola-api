package com.donalola.orders.application;

import com.donalola.CustomerDetails;
import com.donalola.ItemMenuDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "Pedido", description = "Pedidos de comida")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderJson implements Serializable {

    private static final long serialVersionUID = 209762589648195576L;

    @ApiModelProperty(value = "Identificador de la orden", readOnly = true)
    private String id;

    @ApiModelProperty(value = "Fecha de creación", readOnly = true)
    private LocalDateTime createdDateTime;

    @ApiModelProperty(value = "Fecha de actualización", readOnly = true)
    private LocalDateTime modifiedDatetime;

    @ApiModelProperty(value = "Información de cliente", readOnly = true)
    private CustomerDetails customerDetails;

    @ApiModelProperty(value = "Identificador del local", required = true)
    private String foodPlaceId;

    @ApiModelProperty(value = "Estado de la orden", readOnly = true)
    private String status;

    @ApiModelProperty(value = "Comentario")
    private String comment;

    @ApiModelProperty(value = "Método de pago", allowableValues = "CASH|CREDIT_CARD")
    private String paymentMethod;

    @ApiModelProperty(value = "Monto total de la orden", readOnly = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "Ítems del pedido", required = true)
    private List<ItemJson> items;


    @ApiModel(value = "Ítem del pedido")
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ItemJson implements Serializable {

        @ApiModelProperty(value = "ID del menú")
        private String foodMenuId;

        @ApiModelProperty(value = "ID del ítem del menú seleccionado")
        private String itemMenuId;

        @ApiModelProperty(value = "Información del item del seleccionado", readOnly = true)
        private ItemMenuDetails itemMenuDetails;

        @ApiModelProperty(value = "Cantidad")
        private Integer quantity;
    }
}
