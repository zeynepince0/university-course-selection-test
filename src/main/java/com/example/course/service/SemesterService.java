package com.example.course.service;

import com.example.course.dto.SemesterDto;

import java.util.List;

public interface SemesterService {

    SemesterDto create(SemesterDto dto);

    SemesterDto getByName(String name);

    List<SemesterDto> getAll();
}

