package com.donalola.orders;

import com.donalola.CustomerID;

public interface Orders extends Iterable<Order> {

    Orders listTodayFoodPlaceOrders(String foodPlaceId);

    Orders listByCustomerID(CustomerID customerID);

    Orders listByCustomerOnStatus(CustomerID customerID, Order.Status status);

    Orders listTodayFoodPlaceOrdersOnStatus(String foodPlaceId, Order.Status status);

}
