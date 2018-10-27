package com.donalola.foodmenu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDateTime;
import java.util.Collections;

public class FoodMenuTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createAllArguments() {
        FoodMenu foodMenu = new FoodMenu("foodMenuid123", "1", FoodMenu.Status.parse("CLOSED"), LocalDateTime.now(), null, "Menú Regular", null);
    }

    @Test
    public void hasNoItems() {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setItems(Collections.emptyList());
        Assert.assertFalse(foodMenu.hasAnyItem());
    }

    @Test
    public void isAvailable() {
        FoodMenu foodMenu = new FoodMenu();
        Assert.assertTrue(foodMenu.isAvailable());
    }
}