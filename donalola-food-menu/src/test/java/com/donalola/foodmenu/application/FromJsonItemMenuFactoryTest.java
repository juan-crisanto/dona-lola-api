package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenuTestUtil;
import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.domain.factory.ItemMenuFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FromJsonItemMenuFactoryTest {

    private ItemMenuFactory<ItemMenuJson> factory;

    @Before
    public void setUp() throws Exception {
        this.factory = new FromJsonItemMenuFactory();
    }

    @Test
    public void createJson() {
        ItemMenu itemMenu = FoodMenuTestUtil.createItem();
        ItemMenuJson json = this.factory.create(itemMenu);
        Assert.assertNotNull(json);
        System.out.println(json);
    }

}