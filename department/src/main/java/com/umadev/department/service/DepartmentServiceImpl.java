package com.umadev.department.service;

import com.umadev.department.client.EmployeeClient;
import com.umadev.department.dao.DepartmentRepository;
import com.umadev.department.entity.Department;
import com.umadev.department.entity.FullDepartmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    // Dependency injected
    private final EmployeeClient employeeClient;
    @Autowired
    public DepartmentServiceImpl(DepartmentRepository theDepartmentRepository, EmployeeClient theEmployeeClient){
        departmentRepository = theDepartmentRepository;
        employeeClient = theEmployeeClient;
    }

    @Override
    public List<Department> findAll(){
        return departmentRepository.findAll();
    }

    @Override
    public Department save(Department theDepartment){
        return departmentRepository.save(theDepartment);
    }

    @Override
    public FullDepartmentResponse findDepartmentsWithEmployees(Integer employeeId){
        // var is used because Java guess the type of department (Department)
        var department = departmentRepository.findById(employeeId)
                .orElse(
                        Department.builder()
                                .name("NOT_FOUND")
                                .email("NOT_FOUND")
                                .build()); // avoid null pointer exceptions
        // Find all the students from the student client with OpenFeign
        var employees = employeeClient.findAllEmployeesByDepartment(employeeId);
        return  FullDepartmentResponse.builder()
                .name(department.getName())
                .email(department.getEmail())
                .employees(employees)
                .build();
    }
}
