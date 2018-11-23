package com.donalola.orders.infraestructure.service;

import com.donalola.orders.Order;
import com.donalola.orders.domain.dao.OrderRepository;
import com.donalola.orders.domain.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order prepareToDeliver(String orderId) {
        Order order = this.orderRepository.get(orderId);
        order.setReady();
        return this.orderRepository.update(order);
    }

    @Override
    public Order deliver(String orderId) {
        Order order = this.orderRepository.get(orderId);
        order.deliver();
        return this.orderRepository.update(order);
    }
}
