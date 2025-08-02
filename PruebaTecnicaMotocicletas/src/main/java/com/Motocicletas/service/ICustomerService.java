package com.Motocicletas.service;

import com.Motocicletas.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> findAll ();

    Optional<Customer> findById (Long id);

    void createCustomer(Customer customer);

    void deleteById(Long id);
}
