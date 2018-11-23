package com.donalola.orders.domain.factory;

import com.donalola.orders.Order;

public interface OrderFactory<T> {

    Order create(T source);

    T create(Order order);

}
