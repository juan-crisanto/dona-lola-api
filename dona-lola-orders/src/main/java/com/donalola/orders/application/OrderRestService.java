package com.donalola.orders.application;

import com.donalola.application.ValidableParam;
import com.donalola.core.rest.service.BaseController;
import com.donalola.orders.Order;
import com.donalola.orders.Orders;
import com.donalola.orders.domain.OrderManager;
import com.donalola.orders.domain.factory.OrderFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "/by/food-place/{foodPlaceId}")
    @ApiOperation(nickname = "Listar mis órdenes", value = "Listar la órdenes para el día de hoy")
    public List<OrderJson> todayOrders(@PathVariable String foodPlaceId) {
        Orders orders = this.orderManager.listTodayOrdersFoodPlace(foodPlaceId);
        List<OrderJson> orderJsonList = new ArrayList<>();
        orders.forEach(order -> {
            orderJsonList.add(this.orderJsonOrderFactory.create(order));
        });
        return orderJsonList;
    }

}
