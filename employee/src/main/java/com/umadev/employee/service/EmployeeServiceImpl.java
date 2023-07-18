package com.umadev.employee.service;

import com.umadev.employee.dao.EmployeeRepository;
import com.umadev.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private  EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository){
        employeeRepository = theEmployeeRepository;
    }

    @Override
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    @Override
    public void save(Employee theEmployee){
        employeeRepository.save(theEmployee);
    }
}
