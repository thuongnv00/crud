package com.example.appdemo.repository;

import com.example.appdemo.model.Department;
import com.example.appdemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long > {
    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeById(Long id);

    Optional<Employee> findEmployeeByName(String name);

    List<Employee> findByDepartmentId(Long Id);
}
