package com.donalola.customer.domain;

import com.donalola.CustomerID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Customer update(Customer customer);

    Customer get(CustomerID customerID);

}
