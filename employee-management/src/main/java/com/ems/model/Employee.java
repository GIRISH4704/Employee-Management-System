package com.ems.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // @ManyToOne  → many employees can belong to one department
    // @JoinColumn → the foreign key column in the employees table is "department_id"
    // Without @JoinColumn, Hibernate would guess a column name (often wrong)
    // FetchType.LAZY → department data is only loaded from DB when you access it,
    //                   not automatically with every employee query (better performance)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @NotBlank
    @Column(name = "designation", nullable = false)
    private String designation;

    @NotNull
    @Positive
    @Column(name = "salary", nullable = false)
    private Double salary;

    @NotNull
    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;
}
