package com.Motocicletas.controller;

import com.Motocicletas.dto.employee.EmployeeDTO;

import com.Motocicletas.model.Employee;

import com.Motocicletas.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeById (@PathVariable Long id) {

        Optional<Employee> employeeOptional = employeeService.findById(id);

        if(employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            EmployeeDTO employeeDTO = EmployeeDTO.builder()
                    .id(employee.getId())
                    .documentType(employee.getDocumentType())
                    .documentNumber(employee.getDocumentNumber())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .birthDate(employee.getBirthDate())
                    .position(employee.getPosition())
                    .email(employee.getEmail())
                    .phoneNumber(employee.getPhoneNumber())
                    .address(employee.getAddress())
                    .build();

            return ResponseEntity.ok(employeeDTO);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees () {

        List<EmployeeDTO> employeesDTOS = employeeService.findAll()
                .stream()
                .map(employee -> EmployeeDTO.builder()
                        .id(employee.getId())
                        .documentType(employee.getDocumentType())
                        .documentNumber(employee.getDocumentNumber())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .birthDate(employee.getBirthDate())
                        .position(employee.getPosition())
                        .email(employee.getEmail())
                        .phoneNumber(employee.getPhoneNumber())
                        .address(employee.getAddress())
                        .build()).toList();

        return ResponseEntity.ok(employeesDTOS);
    }

    @PostMapping("/employees")
    public ResponseEntity<?> saveEmployee (@RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        employeeService.createEmployee(Employee.builder()
                .documentType(employeeDTO.getDocumentType())
                .documentNumber(employeeDTO.getDocumentNumber())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .birthDate(employeeDTO.getBirthDate())
                .position(employeeDTO.getPosition())
                .email(employeeDTO.getEmail())
                .phoneNumber(employeeDTO.getPhoneNumber())
                .address(employeeDTO.getAddress())
                .build());

        return ResponseEntity.created(new URI("/api/employees")).build();
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){

        Optional<Employee> employeeOptional = employeeService.findById(id);

        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.setDocumentType(employeeDTO.getDocumentType());
            employee.setDocumentNumber(employeeDTO.getDocumentNumber());
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setBirthDate(employeeDTO.getBirthDate());
            employee.setPosition(employeeDTO.getPosition());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhoneNumber(employeeDTO.getPhoneNumber());
            employee.setAddress(employeeDTO.getAddress());
            employeeService.createEmployee(employee);
            return ResponseEntity.ok("Registro Actualizado");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id){

        if(id != null) {
            employeeService.deleteById(id);
            return ResponseEntity.ok("Registro Eliminado");
        }

        return ResponseEntity.badRequest().build();

    }
}
