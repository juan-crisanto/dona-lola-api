package com.donalola.foodmenu.application;

import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.domain.factory.ItemMenuFactory;
import org.springframework.stereotype.Component;

@Component
public class FromJsonItemMenuFactory implements ItemMenuFactory<ItemMenuJson> {

    @Override
    public ItemMenu create(ItemMenuJson source) {
        return ItemMenuJsonToDomainMapper.MAPPER.jsonToDomain(source);
    }

    @Override
    public ItemMenuJson create(ItemMenu itemMenu) {
        return ItemMenuJsonToDomainMapper.MAPPER.domainToJson(itemMenu);
    }
}
