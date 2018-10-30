package com.donalola.orders.domain.dao;

import com.donalola.orders.Order;

public interface OrderRepository {

    Order get(String id);

    Order addOrder(Order order);

    Order update(Order order);


}
