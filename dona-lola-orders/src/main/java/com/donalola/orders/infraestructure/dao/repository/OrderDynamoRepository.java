package com.donalola.orders.infraestructure.dao.repository;

import com.donalola.CustomerID;
import com.donalola.core.SimpleIterable;
import com.donalola.orders.Order;
import com.donalola.orders.Orders;
import com.donalola.orders.domain.dao.OrderRepository;
import com.donalola.orders.domain.factory.OrderFactory;
import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import com.donalola.util.LocalDateTimeUtil;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDynamoRepository implements OrderRepository, Orders {

    private final OrderDynamoCrudRepository orderDynamoCrudRepository;
    private final OrderFactory<OrderDynamoEntity> factory;

    public OrderDynamoRepository(OrderDynamoCrudRepository orderDynamoCrudRepository, OrderFactory<OrderDynamoEntity> factory) {
        this.orderDynamoCrudRepository = orderDynamoCrudRepository;
        this.factory = factory;
    }

    @Override
    public Iterator<Order> iterator() {
        Iterable<OrderDynamoEntity> iterableOfAll = this.orderDynamoCrudRepository.findAll();
        List<Order> orders = new ArrayList<>(IterableUtils.size(iterableOfAll));
        iterableOfAll.forEach(orderDynamoEntity -> {
            orders.add(this.factory.create(orderDynamoEntity));
        });
        return orders.iterator();
    }

    @Override
    public Order get(String id) {
        Optional<OrderDynamoEntity> entity = this.orderDynamoCrudRepository.findById(id);
        if (!entity.isPresent()) {
            throw new IllegalArgumentException("No se han encontrado una orden con el ID" + id);
        }
        return this.factory.create(entity.get());
    }

    @Override
    public Order addOrder(Order order) {
        OrderDynamoEntity entity = this.factory.create(order);
        OrderDynamoEntity savedEntity = this.orderDynamoCrudRepository.save(entity);
        Order newOrder = this.factory.create(savedEntity);
        return newOrder;
    }

    @Override
    public Order update(Order order) {
        OrderDynamoEntity entity = this.factory.create(order);
        OrderDynamoEntity savedEntity = this.orderDynamoCrudRepository.save(entity);
        Order newOrder = this.factory.create(savedEntity);
        return newOrder;
    }

    @Override
    public Orders listTodayFoodPlaceOrders(String foodPlaceId) {
        LocalDateTime todayInitTime = LocalDateTimeUtil.getFrom(LocalDateTime.now(), LocalDateTimeUtil.PERU_ZONE_ID);
        todayInitTime = LocalDateTime.of(todayInitTime.toLocalDate(), LocalTime.MIN);
        List<OrderDynamoEntity> orderDynamoEntityList = this.orderDynamoCrudRepository.findAllByFoodPlaceIdAndCreatedDatetimeAfter(foodPlaceId, todayInitTime);
        return createSimpleIterable(orderDynamoEntityList);
    }

    @Override
    public Orders listAllByFoodPlace(String foodPlaceId) {
        List<OrderDynamoEntity> orderDynamoEntityList = this.orderDynamoCrudRepository.findAllByFoodPlaceId(foodPlaceId);
        return createSimpleIterable(orderDynamoEntityList);
    }

    @Override
    public Orders listByCustomerID(CustomerID customerID) {
        List<OrderDynamoEntity> orderDynamoEntityList = this.orderDynamoCrudRepository.findAllByCustomerId(customerID.toString());
        return createSimpleIterable(orderDynamoEntityList);
    }

    @Override
    public Orders listByCustomerOnStatus(CustomerID customerID, Order.Status status) {
        List<OrderDynamoEntity> orderDynamoEntityList = this.orderDynamoCrudRepository.findAllByCustomerIdAndStatus(customerID.toString(), status.name());
        return createSimpleIterable(orderDynamoEntityList);
    }

    @Override
    public Orders listTodayFoodPlaceOrdersOnStatus(String foodPlaceId, Order.Status status) {
        LocalDateTime todayInitTime = LocalDateTimeUtil.getFrom(LocalDateTime.now(), LocalDateTimeUtil.PERU_ZONE_ID);
        todayInitTime = LocalDateTime.of(todayInitTime.toLocalDate(), LocalTime.MIN);
        List<OrderDynamoEntity> orderDynamoEntityList = this.orderDynamoCrudRepository.findAllByFoodPlaceIdAndCreatedDatetimeAfterAndStatus(foodPlaceId, todayInitTime, status.name());
        return createSimpleIterable(orderDynamoEntityList);
    }

    private Orders createSimpleIterable(Iterable<OrderDynamoEntity> iterable) {
        List<Order> orderList = new ArrayList<>(IterableUtils.size(iterable));
        iterable.forEach(orderDynamoEntity -> {
            orderList.add(this.factory.create(orderDynamoEntity));
        });
        return new SimpleOrderIterable(orderList.iterator());
    }

    public static class SimpleOrderIterable extends SimpleIterable<Order> implements Orders {

        public SimpleOrderIterable(Iterator<Order> iterator) {
            super(iterator);
        }

        @Override
        public Orders listTodayFoodPlaceOrders(String foodPlaceId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Orders listByCustomerID(CustomerID customerID) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Orders listByCustomerOnStatus(CustomerID customerID, Order.Status status) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Orders listTodayFoodPlaceOrdersOnStatus(String foodPlaceId, Order.Status status) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Orders listAllByFoodPlace(String foodPlaceId) {
            throw new UnsupportedOperationException();
        }
    }
}
