package com.umadev.department.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

// By default hibernate use PhysicalNamingStrategy
// if not  @Table(name = "some_table", schema = "development") 
// annotation is provided

public class Department {
    @Id
    private Integer id;
    private String name;
    private String email;
 }
