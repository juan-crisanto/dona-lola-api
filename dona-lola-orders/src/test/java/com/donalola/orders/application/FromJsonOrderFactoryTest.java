package com.donalola.orders.application;

import com.donalola.ItemMenuDetails;
import com.donalola.orders.Order;
import com.donalola.orders.domain.factory.OrderFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.UUID;

public class FromJsonOrderFactoryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private OrderFactory<OrderJson> factory;
    @Mock
    private OrderJson json;
    @Mock
    private OrderJson.ItemJson itemJson;

    @Before
    public void setUp() throws Exception {
        this.factory = new FromJsonOrderFactory();

        Mockito.when(this.itemJson.getQuantity()).thenReturn(3);
        Mockito.when(this.itemJson.getFoodMenuId()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(this.itemJson.getItemMenuId()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(this.itemJson.getItemMenuDetails()).thenReturn(new ItemMenuDetails("Lomo saltado", StringUtils.EMPTY, NumberUtils.createBigDecimal("13.50")));

        Mockito.when(this.json.getPaymentMethod()).thenReturn(Order.PaymentMethod.CASH.name());
        Mockito.when(this.json.getComment()).thenReturn("Por favor un cubierto extra");
        Mockito.when(this.json.getFoodPlaceId()).thenReturn("1");
        Mockito.when(this.json.getItems()).thenReturn(Arrays.asList(this.itemJson));
    }

    @Test
    public void create() {
        Order order = this.factory.create(this.json);
        Assert.assertNotNull(order);

        OrderJson orderJson = this.factory.create(order);

        Assert.assertNotNull(order.getStatus());
        Assert.assertTrue(StringUtils.equals(order.getFoodPlaceID().toString(), orderJson.getFoodPlaceId()));
        Assert.assertTrue(StringUtils.equals(order.getStatus().name(), orderJson.getStatus()));

        System.out.println(order);
        System.out.println(orderJson);
    }
}