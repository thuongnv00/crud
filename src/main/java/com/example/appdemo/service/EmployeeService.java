package com.example.appdemo.service;

import com.example.appdemo.exception.UserNotFoundException;
import com.example.appdemo.model.Employee;
import com.example.appdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepository.save(employee);
    }

    public List<Employee> findAllEmployee() {
     return    employeeRepository.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id)
//                .orElseThrow(() -> new UserNotFoundException("User by id " + id + "not found" ));
                .orElseThrow(() -> new RuntimeException("Employee by id "  + id + " not found"));
    }

    public Employee findEmployeeByName(String name) {
        return employeeRepository.findEmployeeByName(name)
//                .orElseThrow(() -> new UserNotFoundException("User by id " + id + "not found" ));
                .orElseThrow(() -> new RuntimeException("Employee by name "  + name + " not found"));
    }
     public void deleteEmployee(Long id) {
       // employeeRepository.deleteEmployeeById(id);
        employeeRepository.deleteById(id);

     }

    public List<Employee> findByDepartmentId(Long id) {
        return employeeRepository.findByDepartmentId(id);

    }

}
