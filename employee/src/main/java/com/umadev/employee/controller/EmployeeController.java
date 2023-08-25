/*-
 * #%L
 * employee
 * %%
 * Copyright (C) 2015 - 2023 Scalable Capital GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.umadev.employee.controller;

import com.umadev.employee.entity.Employee;
import com.umadev.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee save( @RequestBody Employee employee) {
        //Set the ID to zero/0 just in case it's different in the JSON.
        //ID == 0 will create a new Employee
        employee.setId(0);

        return employeeService.save(employee);
    }

    // Add GET mapping to expose /employees with the list of employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll(){
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/employees/departments/{departmentId}")
    public ResponseEntity<List<Employee>> findAll(
            @PathVariable("departmentId") Integer departmentId
    ) {
        return ResponseEntity.ok(employeeService.findAllEmployeesByDepartment(departmentId));
    }
}
