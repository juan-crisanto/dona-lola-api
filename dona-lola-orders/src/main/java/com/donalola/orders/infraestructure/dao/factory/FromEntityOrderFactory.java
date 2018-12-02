package com.donalola.orders.infraestructure.dao.factory;

import com.donalola.AttentionType;
import com.donalola.CustomerDetails;
import com.donalola.CustomerID;
import com.donalola.FoodPlaceID;
import com.donalola.orders.Order;
import com.donalola.orders.domain.factory.OrderFactory;
import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import com.donalola.util.LocalDateTimeUtil;
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
        order.setCustomerDetails(CustomerDetails.of(source.getCustomerName(), source.getCustomerEmail(), source.getCustomerPhone()));
        if (source.getAttentionType() == null) {
            order.setAttentionType(AttentionType.PICK_UP);
        }
        return order;
    }

    @Override
    public OrderDynamoEntity create(Order order) {
        OrderDynamoEntity entity = OrderEntityToDomainMapper.MAPPER.toEntity(order);
        if (!Optional.ofNullable(entity.getCreatedDatetime()).isPresent()) {
            entity.setCreatedDatetime(LocalDateTimeUtil.getFrom(LocalDateTime.now(), LocalDateTimeUtil.PERU_ZONE_ID));
        }
        if (!Optional.ofNullable(entity.getModifiedDatetime()).isPresent()) {
            entity.setModifiedDatetime(LocalDateTimeUtil.getFrom(LocalDateTime.now(), LocalDateTimeUtil.PERU_ZONE_ID));
        }
        entity.setCustomerId(order.getCustomerID().toString());
        entity.setFoodPlaceId(order.getFoodPlaceID().toString());
        entity.setCustomerName(order.getCustomerDetails().getName());
        entity.setCustomerEmail(order.getCustomerDetails().getEmail());
        entity.setCustomerPhone(order.getCustomerDetails().getPhone());
        return entity;
    }
}
