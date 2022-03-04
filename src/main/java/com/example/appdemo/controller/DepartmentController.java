package com.example.appdemo.controller;

import com.example.appdemo.exception.UserNotFoundException;
import com.example.appdemo.model.Department;
import com.example.appdemo.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;



    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
        Department department = departmentService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not found Department with id = " + id));
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

}
