package com.donalola.orders.application;

import com.donalola.orders.Order;
import com.donalola.orders.domain.factory.OrderFactory;
import org.springframework.stereotype.Component;

@Component
public class FromJsonOrderFactory implements OrderFactory<OrderJson> {

    @Override
    public Order create(OrderJson source) {
        Order order = OrderJsonToDomainMapper.MAPPER.toDomain(source);
        return order;
    }


    @Override
    public OrderJson create(Order order) {
        return OrderJsonToDomainMapper.MAPPER.toJson(order);
    }
}
