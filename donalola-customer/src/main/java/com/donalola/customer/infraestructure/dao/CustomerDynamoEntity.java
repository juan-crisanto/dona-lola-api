package com.donalola.customer.infraestructure.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.donalola.CustomerID;
import com.donalola.Identity;
import com.donalola.commons.dynamodb.util.DynamoDBConverter;
import com.donalola.customer.domain.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "CUSTOMER")
public class CustomerDynamoEntity implements Serializable {

    @Id
    @Getter(onMethod = @__({@DynamoDBHashKey}))
    private String id;

    @DynamoDBTypeConverted(converter = DynamoDBConverter.LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "createdDatetime")
    private LocalDateTime createdDatetime;

    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;

    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;

    @DynamoDBAttribute(attributeName = "identityType")
    private String identityType;

    @DynamoDBAttribute(attributeName = "identity")
    private String identity;

    @DynamoDBAttribute(attributeName = "email")
    private String email;

    @DynamoDBAttribute(attributeName = "phone")
    private String phone;

    public Customer convert() {
        return Customer.builder(new CustomerID(this.id))
                .FirstName(this.firstName)
                .LastName(this.lastName)
                .Identity(Identity.Type.valueOf(this.identityType), this.identity)
                .Email(this.email)
                .Phone(this.phone)
                .build();
    }

    public static CustomerDynamoEntity of(final Customer customer) {
        CustomerDynamoEntity customerDynamoEntity = new CustomerDynamoEntity();
        if (customer.getCustomerID() == null) {
            throw new IllegalArgumentException("CustomerID can't be EMTPY or NULL!");
        }
        customerDynamoEntity.setId(customer.getCustomerID().toString());
        customerDynamoEntity.setFirstName(customer.getCustomerDetails().getName());
        customerDynamoEntity.setLastName(customer.getLastName());
        customerDynamoEntity.setEmail(customer.getCustomerDetails().getEmail());
        customerDynamoEntity.setIdentityType(customer.getIdentity().getType().name());
        customerDynamoEntity.setIdentity(customer.getIdentity().getValue());
        customerDynamoEntity.setPhone(customer.getCustomerDetails().getPhone());
        return customerDynamoEntity;
    }

}
