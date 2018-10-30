package com.donalola.orders.event;

import com.donalola.events.Event;
import com.donalola.orders.Order;

public interface OrderReadyEvent extends Event {

    Order getOrder();
}
