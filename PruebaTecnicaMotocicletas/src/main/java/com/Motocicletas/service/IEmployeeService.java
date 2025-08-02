package com.Motocicletas.service;

import com.Motocicletas.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    List<Employee> findAll ();

    Optional<Employee> findById (Long id);

    void createEmployee(Employee employee);

    void deleteById(Long id);
}
