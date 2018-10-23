package com.donalola.application.event.listener;

import com.donalola.infraestructure.AddedOrderApplicationEvent;
import com.donalola.orders.Order;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewOrderdAddedEventListener implements ApplicationListener<AddedOrderApplicationEvent> {

    @Override
    public void onApplicationEvent(AddedOrderApplicationEvent addedOrderApplicationEvent) {
        Order order = addedOrderApplicationEvent.getOrder();
        System.out.println("se vendió la orden número: " + order.getId());
        for (Order.Item item : order.getItems()) {
            System.out.println("actualizando ítems del menú" + item.getItemMenuID());
        }
    }
}
