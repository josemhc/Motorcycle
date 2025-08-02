package com.Motocicletas.service.implementations;

import com.Motocicletas.model.Customer;
import com.Motocicletas.repository.ICustomerRepository;
import com.Motocicletas.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer product) {
        return customerRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
