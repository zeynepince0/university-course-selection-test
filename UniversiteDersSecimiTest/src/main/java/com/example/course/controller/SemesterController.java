package com.example.course.controller;

import com.example.course.dto.SemesterDto;
import com.example.course.service.SemesterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semesters")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping
    public SemesterDto create(@RequestBody SemesterDto dto) {
        return semesterService.create(dto);
    }

    @GetMapping("/{name}")
    public SemesterDto getByName(@PathVariable String name) {
        return semesterService.getByName(name);
    }

    @GetMapping
    public List<SemesterDto> getAll() {
        return semesterService.getAll();
    }
}
