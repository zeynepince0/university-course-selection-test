package com.example.course.service.impl;

import com.example.course.dto.SemesterDto;
import com.example.course.entity.Semester;
import com.example.course.repository.SemesterRepository;
import com.example.course.service.SemesterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public SemesterDto create(SemesterDto dto) {
        Semester s = new Semester();
        s.setName(dto.getName());
        s.setStartDate(dto.getStartDate());
        s.setEndDate(dto.getEndDate());
        Semester saved = semesterRepository.save(s);
        return toDto(saved);
    }

    @Override
    public SemesterDto getByName(String name) {
        return semesterRepository.findByName(name)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
    }

    @Override
    public List<SemesterDto> getAll() {
        return semesterRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private SemesterDto toDto(Semester s) {
        return new SemesterDto(s.getId(), s.getName(), s.getStartDate(), s.getEndDate());
    }
}

