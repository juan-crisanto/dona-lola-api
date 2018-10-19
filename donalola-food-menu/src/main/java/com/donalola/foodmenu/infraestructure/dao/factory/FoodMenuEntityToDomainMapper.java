package com.donalola.foodmenu.infraestructure.dao.factory;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public interface FoodMenuEntityToDomainMapper {

    FoodMenuEntityToDomainMapper MAPPER = Mappers.getMapper(FoodMenuEntityToDomainMapper.class);

    @Mapping(source = "status", target = "status", qualifiedByName = "valueToStatusTranformation")
    FoodMenu entityToDomain(FoodMenuDynamoEntity entity);

    @Mapping(source = "status", target = "status", qualifiedByName = "statusToStringTransformation")
    FoodMenuDynamoEntity domainToEntity(FoodMenu foodMenu);

    @Named("valueToStatusTranformation")
    default FoodMenu.Status valueToStatusTranformation(String status) {
        return FoodMenu.Status.parse(status);
    }

    @Named("statusToStringTransformation")
    default String statusToStringTransformation(FoodMenu.Status status) {
        return status.name();
    }
}
