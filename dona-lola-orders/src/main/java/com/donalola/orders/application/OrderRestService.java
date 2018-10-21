package com.donalola.orders.application;

import com.donalola.CustomerDetails;
import com.donalola.CustomerID;
import com.donalola.ItemMenuDetails;
import com.donalola.core.rest.service.BaseController;
import com.donalola.orders.Order;
import com.donalola.orders.domain.OrderManager;
import com.donalola.orders.domain.factory.OrderFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public OrderJson newOrder(@RequestBody OrderJson newOrder, BindingResult bindingResult, Principal principal) {
        completeRequest(newOrder, principal);
        Order order = this.orderJsonOrderFactory.create(newOrder);
        order.setCustomerID(new CustomerID(principal.getName()));
        order.setCustomerDetails(new CustomerDetails("JUAN DIEGO", StringUtils.EMPTY, "973864392"));
        return this.orderJsonOrderFactory.create(this.orderManager.addOrder(order, principal));
    }

    private void completeRequest(OrderJson orderJson, Principal principal) {
        List<OrderJson.ItemJson> itemJsonList = new ArrayList<>(orderJson.getItems().size());
        for (OrderJson.ItemJson itemJson : orderJson.getItems()) {
            itemJson.setItemMenuDetails(new ItemMenuDetails("Lomo Saltado", "Lomo saltado con papas fritas y arroz blanco", NumberUtils.createBigDecimal("15.50")));
            itemJsonList.add(itemJson);
        }
        orderJson.setItems(itemJsonList);
    }


}
