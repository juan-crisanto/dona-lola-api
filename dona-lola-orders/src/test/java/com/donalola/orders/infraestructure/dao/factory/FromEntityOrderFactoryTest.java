package com.donalola.orders.infraestructure.dao.factory;

import com.donalola.CustomerID;
import com.donalola.FoodMenuID;
import com.donalola.FoodPlaceID;
import com.donalola.ItemMenuID;
import com.donalola.orders.Order;
import com.donalola.orders.domain.factory.OrderFactory;
import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class FromEntityOrderFactoryTest {

    private OrderFactory<OrderDynamoEntity> factory;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private OrderDynamoEntity orderDynamoEntity;
    @Mock
    private OrderDynamoEntity.ItemEntity itemEntity;

    @Before
    public void setUp() throws Exception {
        factory = new FromEntityOrderFactory();
        Mockito.when(itemEntity.getFoodMenuID()).thenReturn(new FoodMenuID(UUID.randomUUID().toString()));
        Mockito.when(itemEntity.getItemMenuID()).thenReturn(new ItemMenuID(UUID.randomUUID().toString()));
        Mockito.when(itemEntity.getQuantity()).thenReturn(2);
        Mockito.when(itemEntity.getUnitPrice()).thenReturn(NumberUtils.createBigDecimal("12.50"));

        Mockito.when(orderDynamoEntity.getComment()).thenReturn("LLegaré un poco más tarde de las 2:00 pm");
        Mockito.when(orderDynamoEntity.getCreatedDatetime()).thenReturn(LocalDateTime.now());
        Mockito.when(orderDynamoEntity.getCustomerId()).thenReturn("1234567890");
        Mockito.when(orderDynamoEntity.getCustomerName()).thenReturn("Juan Diego Crisanto");
        Mockito.when(orderDynamoEntity.getFoodPlaceId()).thenReturn("abc-123-xyz");
        Mockito.when(orderDynamoEntity.getId()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(orderDynamoEntity.getTotalPrice()).thenReturn(NumberUtils.createBigDecimal("123"));

        Mockito.when(orderDynamoEntity.getItems()).thenReturn(Arrays.asList(itemEntity));
    }

    @Test
    public void testToDomain() {
        Order order = this.factory.create(this.orderDynamoEntity);
        Assert.assertEquals(new CustomerID("1234567890"), order.getCustomerID());
        Assert.assertEquals(new FoodPlaceID("abc-123-xyz"), order.getFoodPlaceID());
        Assert.assertTrue(order.getTotalPrice().compareTo(NumberUtils.createBigDecimal("25")) == 0);

        OrderDynamoEntity entity = this.factory.create(order);
        Assert.assertEquals(entity.getCustomerId(), order.getCustomerID().toString());
        Assert.assertEquals(entity.getFoodPlaceId(), order.getFoodPlaceID().toString());

        System.out.println(order);
        System.out.println(entity);
    }

}