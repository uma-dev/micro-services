package com.umadev.employee.service;

import com.umadev.employee.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    void save(Employee theEmployee);
}
