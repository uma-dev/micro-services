package com.umadev.department.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullDepartmentResponse {

    private String name;
    private String email;

    List<Employee> employees;

}
