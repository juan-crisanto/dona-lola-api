package com.donalola.orders.infraestructure.dao.repository;

import com.donalola.orders.Order;
import com.donalola.orders.domain.dao.OrderRepository;
import com.donalola.orders.domain.factory.OrderFactory;
import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderDynamoRepository implements OrderRepository {

    private final OrderDynamoCrudRepository orderDynamoCrudRepository;
    private final OrderFactory<OrderDynamoEntity> factory;

    public OrderDynamoRepository(OrderDynamoCrudRepository orderDynamoCrudRepository, OrderFactory<OrderDynamoEntity> factory) {
        this.orderDynamoCrudRepository = orderDynamoCrudRepository;
        this.factory = factory;
    }

    @Override
    public Order get(String id) {
        return null;
    }

    @Override
    public Order addOrder(Order order) {
        OrderDynamoEntity entity = this.factory.create(order);
        OrderDynamoEntity savedEntity = this.orderDynamoCrudRepository.save(entity);
        Order newOrder = this.factory.create(savedEntity);
        return newOrder;
    }
}
