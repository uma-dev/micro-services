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

public class Department {
    @Id
    private Integer id;
    private String name;
    private String email;
 }
