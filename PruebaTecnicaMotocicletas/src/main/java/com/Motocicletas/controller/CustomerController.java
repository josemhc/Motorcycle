package com.Motocicletas.controller;

import com.Motocicletas.model.Customer;
import com.Motocicletas.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/customers/{id}")
    public Customer getCustomerById (@PathVariable Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers () {
        return customerService.findAll();
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> saveCustomer (@RequestBody Customer customer) {
        Customer customerSaved = customerService.createCustomer(customer);
        return new ResponseEntity<>(customerSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteById(id);
    }
}
