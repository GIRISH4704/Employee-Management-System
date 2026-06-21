package com.ems.service;

import com.ems.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
}
