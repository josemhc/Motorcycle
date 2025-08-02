package com.Motocicletas.service;

import com.Motocicletas.model.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee findById (Long id);

    List<Employee> findAll ();

    Employee createEmployee(Employee employee);

    void deleteById(Long id);
}
