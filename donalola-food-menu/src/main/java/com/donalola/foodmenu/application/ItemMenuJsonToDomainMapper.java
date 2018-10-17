package com.donalola.foodmenu.application;

import com.donalola.foodmenu.ItemMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMenuJsonToDomainMapper {

    ItemMenuJsonToDomainMapper MAPPER = Mappers.getMapper(ItemMenuJsonToDomainMapper.class);

    ItemMenu jsonToDomain(ItemMenuJson json);

    ItemMenuJson domainToJson(ItemMenu itemMenu);

}
