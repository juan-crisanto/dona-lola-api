package com.donalola.foodmenu.infraestructure.dao.factory;

import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.infraestructure.dao.entity.ItemMenuDynamoEntity;
import org.mapstruct.factory.Mappers;

public interface ItemMenuEntityToDomainMapper {

    ItemMenuEntityToDomainMapper MAPPER = Mappers.getMapper(ItemMenuEntityToDomainMapper.class);

    ItemMenu entityToDomain(ItemMenuDynamoEntity json);

    ItemMenuDynamoEntity domainToEntity(ItemMenu itemMenu);

}
