package com.donalola.orders.domain;

import com.donalola.orders.Order;
import com.donalola.orders.Orders;

import java.security.Principal;

public interface OrderManager {

    Order addOrder(Order order, Principal principal);

    Orders listTodayOrdersFoodPlace(String foodPlaceId);

}
