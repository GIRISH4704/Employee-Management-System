package com.ems.service.impl;

import com.ems.dto.DepartmentDTO;
import com.ems.model.Department;
import com.ems.repository.DepartmentRepository;
import com.ems.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(d -> DepartmentDTO.builder().id(d.getId()).name(d.getName()).build())
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department department = Department.builder().name(dto.getName()).build();
        Department saved = departmentRepository.save(department);
        return DepartmentDTO.builder().id(saved.getId()).name(saved.getName()).build();
    }
}
