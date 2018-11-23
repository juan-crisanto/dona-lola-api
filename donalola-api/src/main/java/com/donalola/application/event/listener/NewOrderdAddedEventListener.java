package com.donalola.application.event.listener;

import com.donalola.foodmenu.domain.FoodMenuManager;
import com.donalola.infraestructure.AddedOrderApplicationEvent;
import com.donalola.orders.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class NewOrderdAddedEventListener implements ApplicationListener<AddedOrderApplicationEvent> {

    private final FoodMenuManager foodMenuManager;

    public NewOrderdAddedEventListener(FoodMenuManager foodMenuManager) {
        this.foodMenuManager = foodMenuManager;
    }

    @Override
    public void onApplicationEvent(AddedOrderApplicationEvent addedOrderApplicationEvent) {
        Order order = addedOrderApplicationEvent.getOrder();
        log.info("[NUEVA ORDEN]:" + order.getId());
        List<Order.Item> takenItems = order.getItems();
        for (Order.Item takenItem : takenItems) {
            foodMenuManager.sellItem(takenItem.getFoodMenuID().toString(), takenItem.getItemMenuID().toString(), takenItem.getQuantity());
            log.info("[MENU ITEM UPDATED]: " + takenItem.getItemMenuID().toString());
        }
    }
}
