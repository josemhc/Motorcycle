package com.Motocicletas.repository;

import com.Motocicletas.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface IEmployeeRepository extends CrudRepository<Employee, Long> {
}
