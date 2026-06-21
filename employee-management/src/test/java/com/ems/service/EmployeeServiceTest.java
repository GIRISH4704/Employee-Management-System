package com.ems.service;

import com.ems.dto.EmployeeDTO;
import com.ems.exception.ResourceNotFoundException;
import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// @ExtendWith tells JUnit 5 to activate Mockito's extension,
// which processes @Mock and @InjectMocks before each test runs
@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    // @Mock creates a fake EmployeeRepository — no real DB, no real SQL
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    // @InjectMocks creates a REAL EmployeeServiceImpl and injects
    // the two mocks above into its constructor (via @RequiredArgsConstructor)
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    // ── Helper builders ───────────────────────────────────────────────────────

    private Department mockDepartment() {
        return Department.builder().id(1L).name("Engineering").build();
    }

    private Employee mockEmployee(Department dept) {
        return Employee.builder()
                .id(1L)
                .firstName("Rahul")
                .lastName("Sharma")
                .email("rahul@ems.com")
                .department(dept)
                .designation("Senior Engineer")
                .salary(95000.0)
                .joiningDate(LocalDate.of(2022, 3, 1))
                .build();
    }

    private EmployeeDTO mockInputDTO() {
        return EmployeeDTO.builder()
                .firstName("Rahul")
                .lastName("Sharma")
                .email("rahul@ems.com")
                .departmentId(1L)
                .designation("Senior Engineer")
                .salary(95000.0)
                .joiningDate(LocalDate.of(2022, 3, 1))
                .build();
    }

    // ── Test 1: createEmployee → returned DTO has correct values ─────────────

    @Test
    void createEmployee_returnsCorrectDTO() {
        Department dept = mockDepartment();

        // when() programs the mock: "if findById(1L) is called, return this department"
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(dept));
        // any(Employee.class) matches any Employee argument passed to save()
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee(dept));

        EmployeeDTO result = employeeService.createEmployee(mockInputDTO());

        // AssertJ assertions — more readable than JUnit assertEquals
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Rahul");
        assertThat(result.getLastName()).isEqualTo("Sharma");
        assertThat(result.getDepartmentName()).isEqualTo("Engineering");
        assertThat(result.getSalary()).isEqualTo(95000.0);

        // verify() asserts these methods were actually called (not just that the result is right)
        verify(departmentRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    // ── Test 2: getEmployeeById with a valid ID → returns the correct DTO ─────

    @Test
    void getEmployeeById_existingId_returnsDTO() {
        Department dept = mockDepartment();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee(dept)));

        EmployeeDTO result = employeeService.getEmployeeById(1L);

        assertThat(result.getFirstName()).isEqualTo("Rahul");
        assertThat(result.getEmail()).isEqualTo("rahul@ems.com");
        assertThat(result.getDepartmentName()).isEqualTo("Engineering");
    }

    // ── Test 3: getEmployeeById with a missing ID → throws ResourceNotFoundException

    @Test
    void getEmployeeById_nonExistentId_throwsResourceNotFoundException() {
        // Return empty Optional — simulates "not found in DB"
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // assertThatThrownBy executes the lambda and asserts the exception type and message
        assertThatThrownBy(() -> employeeService.getEmployeeById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }

    // ── Test 4: getAllEmployees → returns correctly mapped DTOs ───────────────

    @Test
    void getAllEmployees_returnsMappedDTOList() {
        Department dept = mockDepartment();
        when(employeeRepository.findAll()).thenReturn(List.of(mockEmployee(dept)));

        List<EmployeeDTO> results = employeeService.getAllEmployees();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getFirstName()).isEqualTo("Rahul");
        assertThat(results.get(0).getDepartmentName()).isEqualTo("Engineering");
    }

    // ── Test 5: deleteEmployee with missing ID → throws exception ─────────────

    @Test
    void deleteEmployee_nonExistentId_throwsResourceNotFoundException() {
        when(employeeRepository.existsById(42L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteEmployee(42L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("42");

        // Crucial: verify deleteById was NEVER called when the employee didn't exist
        verify(employeeRepository, never()).deleteById(any());
    }
}
