package com.donalola.foodmenu.infraestructure.dao.factory;

import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.domain.factory.ItemMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.ItemMenuDynamoEntity;
import org.springframework.stereotype.Component;

@Component
public class FromDynamoEntityItemMenuFactory implements ItemMenuFactory<ItemMenuDynamoEntity> {

    @Override
    public ItemMenu create(ItemMenuDynamoEntity source) {
        return ItemMenuEntityToDomainMapper.MAPPER.entityToDomain(source);
    }

    @Override
    public ItemMenuDynamoEntity create(ItemMenu itemMenu) {
        return ItemMenuEntityToDomainMapper.MAPPER.domainToEntity(itemMenu);
    }
}
