package com.donalola.orders.domain;

import com.donalola.CustomerID;
import com.donalola.orders.Order;
import com.donalola.orders.Orders;

import java.security.Principal;

public interface OrderManager {

    Order addOrder(Order order, Principal principal);

    Orders listTodayOrdersFoodPlace(String foodPlaceId);

    Orders listByCustomerOnStatus(CustomerID customerID, Order.Status status);

    Orders listByCustomer(CustomerID customerID);

    Order prepareToDeliver(String orderId);

    Order deliver(String orderId);

    Order preparing(String orderId);

}
