package com.donalola.orders.domain;

import com.donalola.CustomerID;
import com.donalola.events.EventPublisher;
import com.donalola.infraestructure.AddedOrderApplicationEvent;
import com.donalola.infraestructure.OrderReadyApplicationEvent;
import com.donalola.orders.Order;
import com.donalola.orders.Orders;
import com.donalola.orders.domain.dao.OrderRepository;
import com.donalola.orders.domain.service.OrderService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class OrderManagerImpl implements OrderManager {

    private final OrderRepository orderRepository;
    private final Orders orders;
    private final OrderService orderService;

    @Setter(onMethod = @__(@Autowired))
    private EventPublisher eventPublisher;

    public OrderManagerImpl(OrderRepository orderRepository, Orders orders, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orders = orders;
        this.orderService = orderService;
    }

    @Override
    public Order addOrder(Order order, Principal principal) {
        Order addedOrder = this.orderRepository.addOrder(order);
        publishNewOrderEvent(addedOrder);
        return addedOrder;
    }

    public Orders listTodayOrdersFoodPlace(String foodPlaceId) {
        return this.orders.listTodayFoodPlaceOrders(foodPlaceId);
    }

    @Override
    public Orders listByCustomerOnStatus(CustomerID customerID, Order.Status status) {
        return this.orders.listByCustomerOnStatus(customerID, status);
    }

    @Override
    public Orders listByCustomer(CustomerID customerID) {
        return this.orders.listByCustomerID(customerID);
    }

    @Override
    public Order prepareToDeliver(String orderId) {
        Order order = this.orderService.prepareToDeliver(orderId);
        publishOrderReadyEvent(order);
        return order;
    }

    @Override
    public Order deliver(String orderId) {
        return this.orderService.deliver(orderId);
    }

    @Override
    public Order preparing(String orderId) {
        return this.orderService.setPreparing(orderId);
    }

    private void publishNewOrderEvent(final Order order) {
        if (Optional.ofNullable(this.eventPublisher).isPresent()) {
            this.eventPublisher.publishEvent(new AddedOrderApplicationEvent(this, order));
        }
    }

    private void publishOrderReadyEvent(final Order order) {
        if (Optional.ofNullable(this.eventPublisher).isPresent()) {
            this.eventPublisher.publishEvent(new OrderReadyApplicationEvent(this, order));
        }
    }
}