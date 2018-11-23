package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FoodMenuJsonToDomainMapper {

    FoodMenuJsonToDomainMapper MAPPER = Mappers.getMapper(FoodMenuJsonToDomainMapper.class);

    FoodMenu jsonToDomain(FoodMenuJson json);

    FoodMenuJson domainToJson(FoodMenu foodMenu);
}
