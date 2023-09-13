package com.umadev.department.service;

import com.umadev.department.entity.Department;
import com.umadev.department.entity.FullDepartmentResponse;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<Department> findAll();

    Optional<Department> findDepartmentById(Integer departmentId);

    FullDepartmentResponse findDepartmentsWithEmployees(Integer departmentId);

    Department save(Department theDepartment);

    void deleteById(Integer departmentId);
}
