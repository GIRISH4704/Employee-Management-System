package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Spring Data reads "Department_Name" and generates:
    // SELECT e.* FROM employees e JOIN departments d ON e.department_id = d.id
    // WHERE d.name = ?
    List<Employee> findByDepartment_Name(String departmentName);

    Optional<Employee> findByEmail(String email);
}
