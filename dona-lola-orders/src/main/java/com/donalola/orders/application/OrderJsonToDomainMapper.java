package com.donalola.orders.application;

import com.donalola.FoodMenuID;
import com.donalola.FoodPlaceID;
import com.donalola.ItemMenuID;
import com.donalola.orders.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderJsonToDomainMapper {

    OrderJsonToDomainMapper MAPPER = Mappers.getMapper(OrderJsonToDomainMapper.class);

    @Mappings({
            @Mapping(source = "foodPlaceId", target = "foodPlaceID", qualifiedByName = "createFoodPlaceID"),
            @Mapping(source = "items", target = "items", qualifiedByName = "itemJsonListToItemList")
    })
    Order toDomain(OrderJson json);

    @Mappings({
            @Mapping(source = "foodPlaceID", target = "foodPlaceId", qualifiedByName = "toStringFoodPlaceID"),
            @Mapping(source = "items", target = "items", qualifiedByName = "itemListToItemJsonList")
    })
    OrderJson toJson(Order order);

    @Named("createFoodPlaceID")
    default FoodPlaceID createFoodPlaceID(String foodPlaceId) {
        return new FoodPlaceID(foodPlaceId);
    }

    @Named("toStringFoodPlaceID")
    default String toStringFoodPlaceID(FoodPlaceID foodPlaceID) {
        return foodPlaceID.toString();
    }


    default Order.Item itemJsonToItem(OrderJson.ItemJson itemJson) {
        if (itemJson == null) {
            return null;
        }

        Order.Item item = new Order.Item();

        item.setItemMenuDetails(itemJson.getItemMenuDetails());
        item.setQuantity(itemJson.getQuantity());
        item.setFoodMenuID(new FoodMenuID(itemJson.getFoodMenuId()));
        item.setItemMenuID(new ItemMenuID(itemJson.getItemMenuId()));

        return item;
    }

    default List<Order.Item> itemJsonListToItemList(List<OrderJson.ItemJson> list) {
        if (list == null) {
            return null;
        }

        List<Order.Item> list1 = new ArrayList<>(list.size());
        for (OrderJson.ItemJson itemJson : list) {
            list1.add(itemJsonToItem(itemJson));
        }

        return list1;
    }

    default OrderJson.ItemJson itemToItemJson(Order.Item item) {
        if (item == null) {
            return null;
        }
        OrderJson.ItemJson itemJson = new OrderJson.ItemJson();
        itemJson.setItemMenuDetails(item.getItemMenuDetails());
        itemJson.setQuantity(item.getQuantity());
        itemJson.setFoodMenuId(item.getFoodMenuID().toString());
        itemJson.setItemMenuId(item.getItemMenuID().toString());
        return itemJson;
    }

    default List<OrderJson.ItemJson> itemListToItemJsonList(List<Order.Item> list) {
        if (list == null) {
            return null;
        }

        List<OrderJson.ItemJson> itemJsonList = new ArrayList<>(list.size());
        for (Order.Item item : list) {
            itemJsonList.add(itemToItemJson(item));
        }

        return itemJsonList;
    }

}
