package com.example.course.service;

import com.example.course.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto create(DepartmentDto dto);

    DepartmentDto getByCode(String code);

    List<DepartmentDto> getAll();
}

