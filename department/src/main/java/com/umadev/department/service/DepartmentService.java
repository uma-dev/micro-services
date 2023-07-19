package com.umadev.department.service;

import com.umadev.department.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    Department save(Department theDepartment);
}
