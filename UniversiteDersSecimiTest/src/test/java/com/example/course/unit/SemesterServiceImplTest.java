package com.example.course.unit;

import com.example.course.dto.SemesterDto;
import com.example.course.entity.Semester;
import com.example.course.repository.SemesterRepository;
import com.example.course.service.impl.SemesterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SemesterServiceImplTest {

    @Mock
    SemesterRepository semesterRepository;

    @InjectMocks
    SemesterServiceImpl semesterService;

    @Test
    void create_success() {
        Semester s = new Semester();
        s.setId(1L);
        s.setName("2024-FALL");

        when(semesterRepository.save(any()))
                .thenReturn(s);

        SemesterDto dto = new SemesterDto(null, "2024-FALL",
                LocalDate.now(), LocalDate.now().plusMonths(4));

        SemesterDto res = semesterService.create(dto);

        assertEquals("2024-FALL", res.getName());
    }

    @Test
    void getByName_success() {
        Semester s = new Semester();
        s.setName("2024-FALL");

        when(semesterRepository.findByName("2024-FALL"))
                .thenReturn(Optional.of(s));

        SemesterDto res = semesterService.getByName("2024-FALL");

        assertEquals("2024-FALL", res.getName());
    }

    @Test
    void getByName_notFound() {
        when(semesterRepository.findByName("X"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> semesterService.getByName("X"));
    }

    @Test
    void getAll_success() {
        when(semesterRepository.findAll())
                .thenReturn(List.of(new Semester()));

        List<SemesterDto> res = semesterService.getAll();

        assertEquals(1, res.size());
    }
}
