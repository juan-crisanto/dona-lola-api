package com.donalola.orders.domain.factory;

import com.donalola.events.EventPublisher;
import com.donalola.infraestructure.AddedOrderApplicationEvent;
import com.donalola.orders.Order;
import com.donalola.orders.domain.OrderManager;
import com.donalola.orders.domain.dao.OrderRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class OrderManagerImpl implements OrderManager {

    private final OrderRepository orderRepository;

    @Setter(onMethod = @__(@Autowired))
    private EventPublisher eventPublisher;

    public OrderManagerImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(Order order, Principal principal) {
        Order addedOrder = this.orderRepository.addOrder(order);
        publishEvent(addedOrder);
        return addedOrder;
    }

    private void publishEvent(final Order order) {
        if (Optional.ofNullable(this.eventPublisher).isPresent()) {
            this.eventPublisher.publishEvent(new AddedOrderApplicationEvent(this, order));
        }
    }
}