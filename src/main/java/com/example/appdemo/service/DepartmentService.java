package com.example.appdemo.service;

import com.example.appdemo.exception.UserNotFoundException;
import com.example.appdemo.model.Department;
import com.example.appdemo.model.Employee;
import com.example.appdemo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public Optional<Department> findById(long id) {
        return departmentRepository.findById(id);

    }
}
