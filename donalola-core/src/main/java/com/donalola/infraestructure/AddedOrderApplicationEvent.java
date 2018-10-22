package com.donalola.infraestructure;

import com.donalola.orders.Order;
import com.donalola.orders.event.AddedOrderEvent;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.security.Principal;

public class AddedOrderApplicationEvent extends ApplicationEvent implements AddedOrderEvent {

    @Setter
    private Principal principal;
    private final Order order;

    public AddedOrderApplicationEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    @Override
    public Order getOrder() {
        return this.order;
    }

    @Override
    public Principal getPrincipal() {
        return this.principal;
    }
}
