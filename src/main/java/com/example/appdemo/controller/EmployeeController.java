package com.example.appdemo.controller;

import com.example.appdemo.config.FormatDate;
import com.example.appdemo.model.Employee;
import com.example.appdemo.model.EmployeeModel;
import com.example.appdemo.service.DepartmentService;
import com.example.appdemo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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



    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    public List<EmployeeModel> getAllEmployees() {

        List<Employee> employees = employeeService.findAllEmployee();
        List<EmployeeModel> employeeModels =  employees.stream().map(employee -> modelMapper.map(employee,EmployeeModel.class)).collect(Collectors.toList());
        for(EmployeeModel employeeModel:employeeModels) {
            for (Employee employee:employees) {
                if((employeeModel.getId() == employee.getId())& employee.getBirthDate()!= null) {
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
        if(employee.getBirthDate()!= null){
            employeeModel.setBirthDate(employee.getBirthDate().format(formatter));
        }
        return employeeModel;
    }



    @PutMapping ("/update")
    public ResponseEntity<EmployeeModel> updateEmployee(@RequestBody EmployeeModel employeeModel) {
        Employee request = modelMapper.map(employeeModel,Employee.class);
        request.setBirthDate(LocalDate.parse(employeeModel.getBirthDate(),formatter));
        Employee employee = employeeService.updateEmployee(request);
        EmployeeModel response = modelMapper.map(employee,EmployeeModel.class);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeModel> addEmployee(@RequestBody EmployeeModel employeeModel) {
        Employee request = modelMapper.map(employeeModel,Employee.class);
        request.setBirthDate(LocalDate.parse(employeeModel.getBirthDate(),formatter));
        Employee employee = employeeService.addEmployee(request);
        EmployeeModel response = modelMapper.map(employee,EmployeeModel.class);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
       employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Delete employee successfully !",HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}/employees")
    public ResponseEntity<List<EmployeeModel>> getAllEmployeesByDepartmentId(@PathVariable(value = "departmentId") Long id) {
        List<Employee> employees = employeeService.findByDepartmentId(id);
        List<EmployeeModel> employeeModels = employees.stream().map(employee -> modelMapper.map(employee,EmployeeModel.class)).collect(Collectors.toList());
        for(EmployeeModel employeeModel:employeeModels) {
            for(Employee employee : employees) {
                if((employeeModel.getId() == employee.getId())& employee.getBirthDate()!= null) {
                    employeeModel.setBirthDate(employee.getBirthDate().format(formatter));
                }
            }
        }
        return new ResponseEntity<>(employeeModels, HttpStatus.OK);
    }

}
