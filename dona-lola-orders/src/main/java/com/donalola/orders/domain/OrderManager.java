package com.donalola.orders.domain;

import com.donalola.orders.Order;

import java.security.Principal;

public interface OrderManager {

    Order addOrder(Order order, Principal principal);

}
