package com.ems.service;

import com.ems.dto.EmployeeDTO;

import java.util.List;

// This is a Java interface — it defines WHAT the service can do, not HOW
// Why an interface?
//   - The Controller depends on this interface, not the concrete class
//   - You can swap implementations (e.g., mock it in tests) without touching the Controller
//   - This is the "D" in SOLID: Dependency Inversion Principle
public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

    List<EmployeeDTO> getEmployeesByDepartment(String department);
}
