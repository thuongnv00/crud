package com.example.appdemo.controller;

import com.example.appdemo.config.FormatDate;
import com.example.appdemo.model.Employee;
import com.example.appdemo.model.EmployeeModel;
import com.example.appdemo.service.DepartmentService;
import com.example.appdemo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private ModelMapper modelMapper;
    private static final DateTimeFormatter  formatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    private final FormatDate formatDate;


    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService, FormatDate formatDate) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.formatDate = formatDate;
    }

    @GetMapping("/all")
    public List<EmployeeModel> getAllEmployees() {

        List<Employee> employees = employeeService.findAllEmployee();
        List<EmployeeModel> employeeModels =  employees.stream().map(employee -> modelMapper.map(employee,EmployeeModel.class)).collect(Collectors.toList());
        for(EmployeeModel employeeModel:employeeModels) {
            for (Employee employee:employees) {
                if(employeeModel.getId() == employee.getId()) {
                    employeeModel.setBirthDate(employee.getBirthDate().format(formatter));
                }
            }
        }
        return employeeModels;
    }

    @GetMapping("/find/{id}")
    public EmployeeModel getEmployeesById(@PathVariable("id") Long id){
        Employee employee = employeeService.findEmployeeById(id);
        EmployeeModel employeeModel = modelMapper.map(employee,EmployeeModel.class);
        employeeModel.setBirthDate(employee.getBirthDate().format(formatter));
        return employeeModel;
    }



    @PutMapping ("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee employeeUpdate = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(employeeUpdate,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws IOException {
            Employee newEmployee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(newEmployee,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
       employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}/employees")
    public ResponseEntity<List<Employee>> getAllEmployeesByDepartmentId(@PathVariable(value = "departmentId") Long id) {

        List<Employee> employees = employeeService.findByDepartmentId(id);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
