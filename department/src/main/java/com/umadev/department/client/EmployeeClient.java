package com.umadev.department.client;

import com.umadev.department.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Responsible for communication and for making an HTTP call to distant API Employee
@FeignClient(name = "employee-service", url = "http://localhost:8090/api/employees")
public interface EmployeeClient {

    @GetMapping("/departments/{departmentId}")
    // Automatic implementation provided by Feign
    List<Employee> findAllEmployeesByDepartment(@PathVariable("departmentId") Integer departmentId);
}
