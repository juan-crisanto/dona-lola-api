package com.donalola.chef.infraestructure.dao.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.donalola.ChefID;
import com.donalola.Identity;
import com.donalola.chef.domain.Chef;
import com.donalola.commons.dynamodb.util.DynamoDBConverter;
import com.donalola.core.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "CHEF")
public class ChefDynamoEntity {

    @Id
    @Getter(onMethod = @__({@DynamoDBHashKey}))
    private String id;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "identityType")
    private String identityType;

    @DynamoDBAttribute(attributeName = "identity")
    private String identity;

    @DynamoDBAttribute(attributeName = "phone")
    private String phone;

    @DynamoDBTypeConverted(converter = DynamoDBConverter.LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "createdDatetime")
    private LocalDateTime createdDatetime;

    @DynamoDBAttribute(attributeName = "address")
    private String address;

    @DynamoDBAttribute(attributeName = "district")
    private String district;

    @DynamoDBAttribute(attributeName = "latitude")
    private String latitude;

    @DynamoDBAttribute(attributeName = "longitude")
    private String longitude;

    @DynamoDBAttribute(attributeName = "image")
    private String image;

    @DynamoDBAttribute(attributeName = "openingSchedule")
    private String openingSchedule;

    @DynamoDBAttribute(attributeName = "closingSchedule")
    private String closingSchedule;

    @DynamoDBAttribute(attributeName = "attentionTypes")
    private List<String> attentionTypes;

    public Chef convert() {
        Location location = new Location();
        location.setAddress(this.address);
        location.setDistrict(this.district);
        location.setLatitude(Double.valueOf(this.latitude));
        location.setLongitude(Double.valueOf(this.longitude));
        if (CollectionUtils.isEmpty(this.attentionTypes)) {
            this.attentionTypes = Arrays.asList(Chef.AttentionType.PICK_UP.name());
        }
        return Chef.builder(ChefID.of(this.id))
                .Base64Image(this.image)
                .Location(location)
                .Name(this.name)
                .Identity(Identity.Type.valueOf(StringUtils.isEmpty(this.identityType) ? "DNI" : this.identityType), this.identity)
                .Phone(this.phone)
                .OpeningOn(this.openingSchedule)
                .ClosingOn(this.closingSchedule)
                .AttentionTypes(this.attentionTypes.parallelStream().map(s -> Chef.AttentionType.valueOf(s)).collect(Collectors.toList()))
                .build();
    }

    public static ChefDynamoEntity of(final Chef chef) {
        ChefDynamoEntity chefDynamoEntity = new ChefDynamoEntity();
        if (chef.getId() == null) {
            throw new IllegalArgumentException("ChefID can't be EMTPY or NULL!");
        }
        chefDynamoEntity.setId(chef.getId().toString());
        chefDynamoEntity.setName(chef.getName());
        chefDynamoEntity.setImage(chef.getImage());
        chefDynamoEntity.setOpeningSchedule(chef.getOpeningSchedule());
        chefDynamoEntity.setClosingSchedule(chef.getClosingSchedule());
        chefDynamoEntity.setLatitude(String.valueOf(chef.getLocation().getLatitude()));
        chefDynamoEntity.setLongitude(String.valueOf(chef.getLocation().getLongitude()));
        chefDynamoEntity.setAddress(chef.getLocation().getAddress());
        chefDynamoEntity.setDistrict(chef.getLocation().getDistrict());
        chefDynamoEntity.setIdentityType(chef.getIdentity().getType().name());
        chefDynamoEntity.setIdentity(chef.getIdentity().getValue());
        chefDynamoEntity.setPhone(chef.getPhone());
        chefDynamoEntity.setAttentionTypes(chef.getAttentionTypes().parallelStream().map(attentionType -> attentionType.name()).collect(Collectors.toList()));
        return chefDynamoEntity;
    }

}
