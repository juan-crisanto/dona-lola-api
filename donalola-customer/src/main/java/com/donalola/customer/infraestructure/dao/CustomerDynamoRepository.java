package com.donalola.customer.infraestructure.dao;

import com.donalola.CustomerID;
import com.donalola.customer.domain.Customer;
import com.donalola.customer.domain.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerDynamoRepository implements CustomerRepository {

    private final CustomerDynamoCrudRepository customerDynamoCrudRepository;

    public CustomerDynamoRepository(CustomerDynamoCrudRepository customerDynamoCrudRepository) {
        this.customerDynamoCrudRepository = customerDynamoCrudRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerDynamoEntity entity = CustomerDynamoEntity.of(customer);
        this.customerDynamoCrudRepository.save(entity);
        return entity.convert();
    }

    @Override
    public Customer update(Customer customer) {
        CustomerDynamoEntity entity = CustomerDynamoEntity.of(customer);
        this.customerDynamoCrudRepository.save(entity);
        return entity.convert();
    }

    @Override
    public Customer get(CustomerID customerID) {
        Optional<CustomerDynamoEntity> entity = this.customerDynamoCrudRepository.findById(customerID.toString());
        if (!entity.isPresent()) {
            throw new IllegalArgumentException("No se encontró un perfil para el ID de usuario " + customerID.toString());
        }
        return entity.get().convert();
    }
}
