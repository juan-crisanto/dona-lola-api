package com.donalola.foodmenu.application;

import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.domain.factory.ItemMenuFactory;
import org.springframework.stereotype.Component;

@Component
public class FromJsonItemMenuFactory implements ItemMenuFactory<ItemMenuJson> {
    @Override
    public ItemMenu create(ItemMenuJson source) {
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setDescription(source.getDescription());
        itemMenu.setId(source.getId());
        itemMenu.setIdMenu(source.getIdMenu());
        itemMenu.setName(source.getName());
        itemMenu.setDescription(source.getDescription());
        itemMenu.setPrice(source.getPrice());
        itemMenu.setQuantityAvailable(source.getQuantityAvailable());
        return itemMenu;
    }

    @Override
    public ItemMenuJson create(ItemMenu itemMenu) {
        return new ItemMenuJson(itemMenu);
    }
}
