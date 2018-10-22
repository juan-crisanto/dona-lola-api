package com.donalola.orders;

public interface Orders extends Iterable<Order> {

    Orders listTodayFoodPlaceOrders(String foodPlaceId);

}
