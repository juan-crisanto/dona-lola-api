package com.donalola.orders.domain.service;

import com.donalola.orders.Order;

public interface OrderService {

    Order prepareToDeliver(String orderId);

    Order deliver(String orderId);

}
