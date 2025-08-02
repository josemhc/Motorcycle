package com.Motocicletas.controller;

import com.Motocicletas.model.Employee;
import com.Motocicletas.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById (@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees () {
        return employeeService.findAll();
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> saveEmployee (@RequestBody Employee employee) {
        Employee employeeSaved = employeeService.createEmployee(employee);
        return new ResponseEntity<>(employeeSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteById(id);
    }
}
