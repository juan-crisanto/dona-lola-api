package com.donalola.orders.application;

import com.donalola.application.PreventCorruption;
import com.donalola.application.ValidableParam;
import com.donalola.core.rest.service.BaseController;
import com.donalola.orders.Order;
import com.donalola.orders.domain.OrderManager;
import com.donalola.orders.domain.factory.OrderFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(
        value = "/api/orders",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@Api(
        value = "Maneja las órdenes",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@PreventCorruption
public class OrderRestService extends BaseController {

    private final OrderFactory<OrderJson> orderJsonOrderFactory;
    private final OrderManager orderManager;

    public OrderRestService(OrderFactory<OrderJson> orderJsonOrderFactory, OrderManager orderManager) {
        this.orderJsonOrderFactory = orderJsonOrderFactory;
        this.orderManager = orderManager;
    }

    @PutMapping
    @ApiOperation(nickname = "Nuevo Pedido", value = "Se ingresa un nuevo pedido al sistema")
    public OrderJson newOrder(@ValidableParam @RequestBody OrderJson newOrder, BindingResult bindingResult, Principal principal) {
        Order order = this.orderJsonOrderFactory.create(newOrder);
        return this.orderJsonOrderFactory.create(this.orderManager.addOrder(order, principal));
    }

}
