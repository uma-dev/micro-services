package com.umadev.employee.controller;

import com.umadev.employee.entity.Employee;
import com.umadev.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @RestController return JSON
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {

    // Inject employee DAO
    private EmployeeService employeeService;

    // Constructor injection
    // As of Spring Framework 4.3, an @Autowired annotation on such a
    // constructor is no longer necessary if the target bean only defines
    // one constructor to begin with. However, if several constructors
    //are available, at least one must be annotated to teach the
    //container which one to use.
    @Autowired
    public EmployeeController( EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    // Add POST mapping for creating a new Employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee save( @RequestBody Employee employee) {
        //Set the ID to zero/0 just in case it's different in the JSON.
        //ID == 0 will create a new Employee
        employee.setId(0);

        return employeeService.save(employee);
    }

    // Add GET mapping to expose /employees with the list of employees
    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }
}
