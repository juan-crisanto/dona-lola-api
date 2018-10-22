package com.donalola.orders.infraestructure.dao.factory;

import com.donalola.CustomerDetails;
import com.donalola.CustomerID;
import com.donalola.FoodPlaceID;
import com.donalola.orders.Order;
import com.donalola.orders.domain.factory.OrderFactory;
import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class FromEntityOrderFactory implements OrderFactory<OrderDynamoEntity> {

    @Override
    public Order create(final OrderDynamoEntity source) {
        Order order = OrderEntityToDomainMapper.MAPPER.toDomain(source);
        order.setCustomerID(new CustomerID(source.getCustomerId()));
        order.setFoodPlaceID(new FoodPlaceID(source.getFoodPlaceId()));
        order.setCustomerDetails(new CustomerDetails(source.getCustomerName(), null, null));
        return order;

    }

    @Override
    public OrderDynamoEntity create(Order order) {
        OrderDynamoEntity entity = OrderEntityToDomainMapper.MAPPER.toEntity(order);
        if (!Optional.ofNullable(entity.getCreatedDatetime()).isPresent()) {
            entity.setCreatedDatetime(LocalDateTime.now());
        }
        if (!Optional.ofNullable(entity.getModifiedDatetime()).isPresent()) {
            entity.setModifiedDatetime(LocalDateTime.now());
        }
        entity.setCustomerId(order.getCustomerID().toString());
        entity.setFoodPlaceId(order.getFoodPlaceID().toString());
        entity.setCustomerName(order.getCustomerDetails().getName());
        return entity;
    }
}
