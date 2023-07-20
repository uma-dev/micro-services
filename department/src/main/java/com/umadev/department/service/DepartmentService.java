package com.umadev.department.service;

import com.umadev.department.entity.Department;
import com.umadev.department.entity.FullDepartmentResponse;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    Department save(Department theDepartment);

    FullDepartmentResponse findDepartmentsWithEmployees(Integer employeeId);
}
