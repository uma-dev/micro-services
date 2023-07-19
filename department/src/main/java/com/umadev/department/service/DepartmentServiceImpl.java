package com.umadev.department.service;

import com.umadev.department.dao.DepartmentRepository;
import com.umadev.department.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    @Autowired
    public DepartmentServiceImpl(DepartmentRepository theDepartmentRepository){
        departmentRepository = theDepartmentRepository;
    }

    @Override
    public List<Department> findAll(){
        return departmentRepository.findAll();
    }

    @Override
    public Department save(Department theDepartment){
        return departmentRepository.save(theDepartment);
    }
}
