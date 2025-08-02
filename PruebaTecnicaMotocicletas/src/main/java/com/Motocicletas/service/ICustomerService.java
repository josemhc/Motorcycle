package com.Motocicletas.service;

import com.Motocicletas.model.Customer;

import java.util.List;

public interface ICustomerService {
    Customer findById (Long id);

    List<Customer> findAll ();

    Customer createCustomer(Customer customer);

    void deleteById(Long id);
}
