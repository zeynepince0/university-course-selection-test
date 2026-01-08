package com.example.course.integration;

import com.example.course.dto.DepartmentDto;
import com.example.course.repository.DepartmentRepository;
import com.example.course.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DepartmentServiceIT {

    @Autowired DepartmentService departmentService;
    @Autowired DepartmentRepository departmentRepository;

    @Test
    void createAndRetrieveDepartment() {
        DepartmentDto dto = new DepartmentDto(null, "DEP1", "Department 1");
        DepartmentDto created = departmentService.create(dto);

        assertNotNull(created.getId());
        assertEquals("DEP1", created.getCode());

        DepartmentDto fetched = departmentService.getByCode("DEP1");
        assertEquals("Department 1", fetched.getName());

        List<DepartmentDto> all = departmentService.getAll();
        assertTrue(all.stream().anyMatch(d -> "DEP1".equals(d.getCode())));
    }
}