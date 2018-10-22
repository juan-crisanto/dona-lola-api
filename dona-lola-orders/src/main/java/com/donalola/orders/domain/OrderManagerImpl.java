package com.donalola.orders.domain;

import com.donalola.events.EventPublisher;
import com.donalola.infraestructure.AddedOrderApplicationEvent;
import com.donalola.orders.Order;
import com.donalola.orders.Orders;
import com.donalola.orders.domain.dao.OrderRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class OrderManagerImpl implements OrderManager {

    private final OrderRepository orderRepository;
    private final Orders orders;

    @Setter(onMethod = @__(@Autowired))
    private EventPublisher eventPublisher;

    public OrderManagerImpl(OrderRepository orderRepository, Orders orders) {
        this.orderRepository = orderRepository;
        this.orders = orders;
    }

    @Override
    public Order addOrder(Order order, Principal principal) {
        Order addedOrder = this.orderRepository.addOrder(order);
        publishEvent(addedOrder);
        return addedOrder;
    }

    public Orders listTodayOrdersFoodPlace(String foodPlaceId) {
        return this.orders.listTodayFoodPlaceOrders(foodPlaceId);
    }

    private void publishEvent(final Order order) {
        if (Optional.ofNullable(this.eventPublisher).isPresent()) {
            this.eventPublisher.publishEvent(new AddedOrderApplicationEvent(this, order));
        }
    }
}