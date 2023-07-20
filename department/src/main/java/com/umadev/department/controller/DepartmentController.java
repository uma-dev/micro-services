package com.umadev.department.controller;

import com.umadev.department.entity.Department;
import com.umadev.department.entity.FullDepartmentResponse;
import com.umadev.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @RestController return JSON
@RequestMapping("/api")
@RequiredArgsConstructor
public class DepartmentController {

    // Inject department DAO
    private DepartmentService departmentService;

    // Constructor injection
    // As of Spring Framework 4.3, an @Autowired annotation on such a
    // constructor is no longer necessary if the target bean only defines
    // one constructor to begin with. However, if several constructors
    //are available, at least one must be annotated to teach the
    //container which one to use.
    @Autowired
    public DepartmentController(DepartmentService theDepartmentService){
        departmentService = theDepartmentService;
    }

    // Add POST mapping for creating a new Department
    @PostMapping("/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public Department save(@RequestBody Department department) {
        //Set the ID to zero/0 just in case it's different in the JSON.
        //ID == 0 will create a new Department
        department.setId(0);

        return departmentService.save(department);
    }

    // Add GET mapping to expose /departments with the list of departments
    @GetMapping("/departments")
    public ResponseEntity<List<Department>>  findAll(){
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/departments/with-employees/{employeeId}")
    public ResponseEntity<FullDepartmentResponse> findAll(
            @PathVariable("employeeId") Integer employeeId
    ) {
        return ResponseEntity.ok(departmentService.findDepartmentsWithEmployees(employeeId));
    }


}
