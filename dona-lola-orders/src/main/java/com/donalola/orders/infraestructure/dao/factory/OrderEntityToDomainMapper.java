package com.donalola.orders.infraestructure.dao.factory;

import com.donalola.ItemMenuDetails;
import com.donalola.orders.Order;
import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderEntityToDomainMapper {

    OrderEntityToDomainMapper MAPPER = Mappers.getMapper(OrderEntityToDomainMapper.class);

    @Mapping(source = "items", target = "items", qualifiedByName = "entityListToItemList")
    Order toDomain(OrderDynamoEntity orderDynamoEntity);

    @Mapping(source = "items", target = "items", qualifiedByName = "itemListToEntityList")
    OrderDynamoEntity toEntity(Order order);

    @Named("entityListToItemList")
    default List<Order.Item> itemEntityListToItemList(final List<OrderDynamoEntity.ItemEntity> itemEntityList) {
        if (itemEntityList == null) {
            return null;
        }
        List<Order.Item> itemList = new ArrayList<>(CollectionUtils.size(itemEntityList));
        for (OrderDynamoEntity.ItemEntity itemEntity : itemEntityList) {
            itemList.add(itemEntityToItem(itemEntity));
        }
        return itemList;
    }

    default Order.Item itemEntityToItem(final OrderDynamoEntity.ItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        }
        Order.Item item = new Order.Item();
        item.setQuantity(itemEntity.getQuantity());
        item.setFoodMenuID(itemEntity.getFoodMenuID());
        item.setItemMenuID(itemEntity.getItemMenuID());
        item.setItemMenuDetails(new ItemMenuDetails(itemEntity.getName(), StringUtils.EMPTY, itemEntity.getUnitPrice()));
        return item;
    }

    @Named("itemListToEntityList")
    default List<OrderDynamoEntity.ItemEntity> itemListToEntityList(final List<Order.Item> itemList) {
        if (itemList == null) {
            return null;
        }
        List<OrderDynamoEntity.ItemEntity> itemEntityList = new ArrayList<>(CollectionUtils.size(itemList));
        for (Order.Item item : itemList) {
            itemEntityList.add(itemToItemEntity(item));
        }
        return itemEntityList;
    }

    default OrderDynamoEntity.ItemEntity itemToItemEntity(final Order.Item item) {
        if (item == null) {
            return null;
        }
        OrderDynamoEntity.ItemEntity entity = new OrderDynamoEntity.ItemEntity();
        entity.setFoodMenuID(item.getFoodMenuID());
        entity.setItemMenuID(item.getItemMenuID());
        entity.setQuantity(item.getQuantity());
        entity.setName(item.getItemMenuDetails().getName());
        entity.setUnitPrice(item.getItemMenuDetails().getPrice());
        return entity;
    }

}
