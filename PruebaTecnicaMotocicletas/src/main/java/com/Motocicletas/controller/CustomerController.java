package com.Motocicletas.controller;

import com.Motocicletas.dto.customer.CustomerDTO;
import com.Motocicletas.model.Customer;
import com.Motocicletas.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomerById (@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);

        if(customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            CustomerDTO customerDTO = CustomerDTO.builder()
                    .id(customer.getId())
                    .documentType(customer.getDocumentType())
                    .documentNumber(customer.getDocumentNumber())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .email(customer.getEmail())
                    .phoneNumber(customer.getPhoneNumber())
                    .build();

            return ResponseEntity.ok(customerDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers () {

        List<CustomerDTO> customersDTOS = customerService.findAll()
                .stream()
                .map(customer -> CustomerDTO.builder()
                        .id(customer.getId())
                        .documentType(customer.getDocumentType())
                        .documentNumber(customer.getDocumentNumber())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .email(customer.getEmail())
                        .phoneNumber(customer.getPhoneNumber())
                        .build()).toList();

        return ResponseEntity.ok(customersDTOS);
    }

    @PostMapping("/customers")
    public ResponseEntity<?> saveCustomer (@RequestBody CustomerDTO customerDTO) throws URISyntaxException {

        customerService.createCustomer(Customer.builder()
                .documentType(customerDTO.getDocumentType())
                .documentNumber(customerDTO.getDocumentNumber())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .phoneNumber(customerDTO.getPhoneNumber())
                .build());

        return ResponseEntity.created(new URI("/api/customers")).build();

    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){

        Optional<Customer> customerOptional = customerService.findById(id);

        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            customer.setDocumentType(customerDTO.getDocumentType());
            customer.setDocumentNumber(customerDTO.getDocumentNumber());
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setEmail(customerDTO.getEmail());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customerService.createCustomer(customer);
            return ResponseEntity.ok("Registro Actualizado");
        }

        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id){

        if(id == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Customer> customer = customerService.findById(id);

        if(!customer.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id no encontrado");
        }

        if(!customer.get().getSales().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar el cliente porque tiene ventas asociadas.");
        }

            customerService.deleteById(id);
            return ResponseEntity.ok("Registro Eliminado");

    }
}
