package com.umadev.employee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Employee {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer departmentId;
}
