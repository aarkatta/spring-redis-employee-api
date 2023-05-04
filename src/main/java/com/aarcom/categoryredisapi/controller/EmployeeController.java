package com.aarcom.categoryredisapi.controller;

import com.aarcom.categoryredisapi.exception_handler.EmployeeNotFound;
import com.aarcom.categoryredisapi.model.Employee;
import com.aarcom.categoryredisapi.model.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping("/employees")
    public List<Employee> getAll() {
        return service.getAllEmployees();
    }

    @GetMapping("/employees/{empId}")
    public Employee getEmployeeById(@PathVariable int empId) {
        Employee employee = service.getEmployeeById(empId);
        if(null == employee)
            throw new EmployeeNotFound("Employee Not Found");

        return employee;
    }

    @GetMapping(value = "/employees/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEmployeeByName(@PathVariable String name) {
        String employee = service.getEmployeeByName(name);
        if(null == employee)
            throw new EmployeeNotFound("Employee Not Found");

        return employee;
    }

    @PostMapping("/employees/user")
    public ResponseEntity<Object> saveEmployee(@RequestBody Employee emp) {
        Employee newEmployee = service.saveEmployee(emp);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{employeeId}")
                .buildAndExpand(newEmployee.getEmployeeId())
                .toUri();
        return  ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/employees/delete/{empId}")
    public void deleteEmployee(@PathVariable int empId) {

        Employee emp = service.deleteEmployee(empId);
        if(null == emp)
            throw new EmployeeNotFound("Employee Not Found");

    }

}

