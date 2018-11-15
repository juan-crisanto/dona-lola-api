package com.donalola.customer.domain;

import com.donalola.CustomerDetails;
import com.donalola.CustomerID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Setter(AccessLevel.PRIVATE)
@Getter
public class Customer implements Serializable {

    private static final long serialVersionUID = -5915009947121256194L;

    private CustomerID customerID;
    private CustomerDetails customerDetails;
    private Identity identity;
    private String lastName;

    private Customer(final Builder builder) {
        this.customerID = builder.getCustomerID();
        this.customerDetails = builder.getCustomerDetails();
        this.lastName = builder.getLastName();
        this.identity = builder.getIdentity();
    }

    private String getName() {
        return this.customerDetails.getName();
    }

    private String getFullName() {
        return this.customerDetails.getName() + " " + this.lastName;
    }

    public static Builder builder(CustomerID customerID) {
        return new Builder(customerID);
    }


    @Getter(AccessLevel.PRIVATE)
    public static class Builder {

        private CustomerID customerID;
        private Identity identity;
        private String name;
        private String lastName;
        private String email;
        private String phone;
        private CustomerDetails customerDetails;


        private Builder(CustomerID customerID) {
            this.customerID = customerID;
        }

        public Builder FirstName(String firstName) {
            if (StringUtils.isEmpty(firstName)) {
                throw new IllegalArgumentException("Customer name can't be null or EMPTY");
            }
            this.name = firstName;
            return this;
        }

        public Builder LastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder Identity(Identity.Type type, String value) {
            this.identity = Identity.of(value, type);
            return this;
        }

        public Builder Email(String email) {
            this.email = email;
            return this;
        }

        public Builder Phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Customer build() {
            this.customerDetails = CustomerDetails.of(this.name, this.email, this.phone);
            return new Customer(this);
        }

    }

}
