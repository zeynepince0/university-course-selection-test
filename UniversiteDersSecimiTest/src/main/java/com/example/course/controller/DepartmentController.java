package com.example.course.controller;

import com.example.course.dto.DepartmentDto;
import com.example.course.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentDto create(@RequestBody DepartmentDto dto) {
        return departmentService.create(dto);
    }

    @GetMapping("/{code}")
    public DepartmentDto getByCode(@PathVariable String code) {
        return departmentService.getByCode(code);
    }

    @GetMapping
    public List<DepartmentDto> getAll() {
        return departmentService.getAll();
    }
}

