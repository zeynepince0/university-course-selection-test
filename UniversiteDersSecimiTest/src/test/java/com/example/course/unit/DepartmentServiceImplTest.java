package com.example.course.unit;

import com.example.course.dto.DepartmentDto;
import com.example.course.entity.Department;
import com.example.course.repository.DepartmentRepository;
import com.example.course.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Test
    void create_success() {
        Department d = new Department();
        d.setId(1L);
        d.setCode("CSE");
        d.setName("Computer Engineering");

        when(departmentRepository.save(any()))
                .thenReturn(d);

        DepartmentDto dto = new DepartmentDto(null, "CSE", "Computer Engineering");

        DepartmentDto res = departmentService.create(dto);

        assertEquals("CSE", res.getCode());
    }

    @Test
    void getByCode_success() {
        Department d = new Department();
        d.setId(1L);
        d.setCode("CSE");

        when(departmentRepository.findByCode("CSE"))
                .thenReturn(Optional.of(d));

        DepartmentDto res = departmentService.getByCode("CSE");

        assertEquals("CSE", res.getCode());
    }

    @Test
    void getByCode_notFound() {
        when(departmentRepository.findByCode("X"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> departmentService.getByCode("X"));
    }

    @Test
    void getAll_success() {
        when(departmentRepository.findAll())
                .thenReturn(List.of(new Department()));

        List<DepartmentDto> res = departmentService.getAll();

        assertEquals(1, res.size());
    }
}
