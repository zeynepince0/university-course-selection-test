package com.example.course.service.impl;

import com.example.course.dto.DepartmentDto;
import com.example.course.entity.Department;
import com.example.course.repository.DepartmentRepository;
import com.example.course.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDto create(DepartmentDto dto) {
        Department dept = new Department();
        dept.setCode(dto.getCode());
        dept.setName(dto.getName());
        Department saved = departmentRepository.save(dept);
        return toDto(saved);
    }

    @Override
    public DepartmentDto getByCode(String code) {
        return departmentRepository.findByCode(code)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @Override
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private DepartmentDto toDto(Department d) {
        return new DepartmentDto(d.getId(), d.getCode(), d.getName());
    }
}
